import React, {Component} from "react";
import MedicationPlanService from "../../services/medication-plan.service";
import {Link, Redirect} from "react-router-dom";
import MedicationService from "../../services/medication.service";
import {connect} from "react-redux";

class CreateMedicationPlan extends Component {
    constructor(props) {
        super(props);
        this.onChangeStart = this.onChangeStart.bind(this);
        this.onChangeEnd = this.onChangeEnd.bind(this);
        this.onChangeIntakeInterval = this.onChangeIntakeInterval.bind(this);
        this.onChangeIntakeIntervalEnd = this.onChangeIntakeIntervalEnd.bind(this);
        this.saveMedicationPlan = this.saveMedicationPlan.bind(this);
        this.newMedicationPlan = this.newMedicationPlan.bind(this);
        this.populateMedications = this.populateMedications.bind(this);
        this.onChangeMedicationDrop = this.onChangeMedicationDrop.bind(this);
        this.addToList = this.addToList.bind(this);

        this.state = {
            id: null,
            start: "",
            end: "",
            medicationDetails: [],
            selectedMed: null,
            intakeInterval: null,
            intakeIntervalEnd: null,

            medications: [],

            submitted: false,
            error: false,
        };
    }

    componentDidMount() {
        this.populateMedications();
    }

    addToList() {
        const x = this.state.selectedMed.split("+")
        const obj = {
            medication: {
                id: x[0],
                name: x[1]
            },
            intakeInterval: this.state.intakeInterval
        }
        const list = this.state.medicationDetails;
        list.push(obj);
        this.setState({
            medicationDetails: list
        });
    }

    onChangeMedicationDrop(e) {
        this.setState({
            selectedMed: e.target.value
        });
    }

    onChangeIntakeInterval(e) {
        this.setState({
            intakeInterval: e.target.value
        });
    }

    onChangeIntakeIntervalEnd(e) {
        this.setState({
            intakeIntervalEnd: e.target.value
        });
    }

    populateMedications() {
        MedicationService.getAllMedications()
            .then((response) => {
                return response.data;
            })
            .then((data) => {
                let medicationsFromApi = data.map(medication => {
                    return {value: `${medication.id}+${medication.name}`, display: `${medication.name}`}
                });
                this.setState({
                    medications: [{value: "", display: 'Select Medication'}].concat(medicationsFromApi)
                });
            }).catch(error => {
            console.log(error);
        });
    }

    onChangeStart(e) {
        this.setState({
            start: e.target.value
        });
    }

    onChangeEnd(e) {
        this.setState({
            end: e.target.value
        });
    }

    saveMedicationPlan() {
        let data = {
            periodStart: this.state.start,
            periodEnd: this.state.end,
            medications: this.state.medicationDetails,
        };

        MedicationPlanService.createMedicationPlan(this.props.match.params.patientId, data)
            .then(response => {
                this.setState({
                    id: response.data.id,
                    start: response.data.periodStart,
                    end: response.data.periodEnd,
                    medicationDetails: response.data.medications,

                    submitted: true
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    error: true
                })
            });
    }

    newMedicationPlan() {
        this.setState({
            id: null,
            start: "",
            end: "",
            medicationDetails: [],

            medications: [],

            submitted: false
        });
    }


    render() {
        const {user: currentUser} = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }

        if (!currentUser.roles.includes("DOCTOR")) {
            return <Redirect to="/home"/>;
        }

        if (this.state.error) {
            return <Redirect to="/error"/>
        }

        return (
            <div className="submit-form">
                {this.state.submitted ? (
                    <div>
                        <h4>You submitted successfully!</h4>
                        <button className="btn btn-success" onClick={this.newMedicationPlan}>
                            Add
                        </button>
                        <Link to={`/patients/${this.props.match.params.patientId}/medication-plans`}>
                            <button className="m-3 btn btn-primary">
                                Back
                            </button>
                        </Link>
                    </div>
                ) : (
                    <div>

                        <div className="form-group">
                            <label htmlFor="start">Start from</label>
                            <input
                                type="date"
                                className="form-control"
                                id="start"
                                required
                                value={this.state.start}
                                onChange={this.onChangeStart}
                                name="birthDate"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="end">End in</label>
                            <input
                                type="date"
                                className="form-control"
                                id="end"
                                required
                                value={this.state.end}
                                onChange={this.onChangeEnd}
                                name="birthDate"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="selectedMed">Medicine</label>
                            <select id="selectedMed" name="selectedMed" className="form-control"
                                    onChange={this.onChangeMedicationDrop}>
                                {this.state.medications.map((medication) => <option key={medication.value}
                                                                                    value={medication.value}>{medication.display}</option>)}
                            </select>
                        </div>

                        <div className="form-group">
                            <label htmlFor="intakeInterval">Intake interval start (hours)</label>
                            <input
                                type="int"
                                className="form-control"
                                id="intakeInterval"
                                required
                                value={this.state.intakeInterval}
                                onChange={this.onChangeIntakeInterval}
                                name="intakeInterval"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="intakeIntervalEnd">Intake interval end (hours)</label>
                            <input
                                type="int"
                                className="form-control"
                                id="intakeIntervalEnd"
                                required
                                value={this.state.intakeIntervalEnd}
                                onChange={this.onChangeIntakeIntervalEnd}
                                name="intakeIntervalEnd"
                            />
                        </div>

                        <button onClick={this.saveMedicationPlan} className="btn btn-success">
                            Submit
                        </button>


                        <button onClick={this.addToList} className="btn btn-dark">
                            Add to list
                        </button>

                        <div>
                            <ul className="list-group">
                                {
                                    this.state.medicationDetails.map((entry, index) => (
                                        <li
                                            className="list-group-item-secondary"
                                            key={index}
                                        >
                                            {entry.medication.name} to be taken between {entry.intakeInterval} and {entry.intakeIntervalEnd} hours
                                        </li>
                                    ))}
                            </ul>
                        </div>

                    </div>
                )}
            </div>
        );
    }
}

function mapStateToProps(state) {
    const {user} = state.auth;
    return {
        user,
    };
}

export default connect(mapStateToProps)(CreateMedicationPlan);
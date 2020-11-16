import React, {Component} from "react";
import MedicationService from "../../services/medication.service";
import {Link, Redirect} from "react-router-dom";
import {connect} from "react-redux";

class CreateMedication extends Component {
    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeSideEffects = this.onChangeSideEffects.bind(this);
        this.onChangeDosage = this.onChangeDosage.bind(this);
        this.onChangeDosageUnits = this.onChangeDosageUnits.bind(this);
        this.saveMedication = this.saveMedication.bind(this);
        this.newMedication = this.newMedication.bind(this);

        this.state = {
            id: null,
            name: "",
            sideEffects: "",
            dosage: 0.0,
            dosageUnits: "",

            submitted: false,
            error: false
        };
    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        });
    }

    onChangeSideEffects(e) {
        this.setState({
            sideEffects: e.target.value
        });
    }

    onChangeDosage(e) {
        this.setState({
            dosage: e.target.value
        });
    }

    onChangeDosageUnits(e) {
        this.setState({
            dosageUnits: e.target.value
        });
    }

    saveMedication() {
        let data = {
            name: this.state.name,
            sideEffects: this.state.sideEffects,
            dosage: this.state.dosage,
            dosageUnits: this.state.dosageUnits
        };

        MedicationService.createMedication(data)
            .then(response => {
                this.setState({
                    id: response.data.id,
                    name: response.data.name,
                    sideEffects: response.data.sideEffects,
                    dosage: response.data.dosage,
                    dosageUnits: response.data.dosageUnits,

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

    newMedication() {
        this.setState({
            id: null,
            name: "",
            sideEffects: "",
            dosage: 0.0,
            dosageUnits: "",

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
                        <button className="btn btn-success" onClick={this.newMedication}>
                            Add
                        </button>
                        <Link to={'/medications'}>
                            <button className="m-3 btn btn-primary">
                                Back
                            </button>
                        </Link>
                    </div>
                ) : (
                    <div>
                        <div className="form-group">
                            <label htmlFor="name">Name</label>
                            <input
                                type="text"
                                className="form-control"
                                id="name"
                                required
                                value={this.state.name}
                                onChange={this.onChangeName}
                                name="name"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="sideEffects">Side Effects</label>
                            <input
                                type="text"
                                className="form-control"
                                id="sideEffects"
                                required
                                value={this.state.sideEffects}
                                onChange={this.onChangeSideEffects}
                                name="sideEffects"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="dosage">Dosage</label>
                            <input
                                type="text"
                                className="form-control"
                                id="dosage"
                                required
                                value={this.state.dosage}
                                onChange={this.onChangeDosage}
                                name="dosage"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="dosageUnits">Dosage Units</label>
                            <input
                                type="text"
                                className="form-control"
                                id="dosageUnits"
                                required
                                value={this.state.dosageUnits}
                                onChange={this.onChangeDosageUnits}
                                name="dosageUnits"
                            />
                        </div>

                        <button onClick={this.saveMedication} className="btn btn-success">
                            Submit
                        </button>
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

export default connect(mapStateToProps)(CreateMedication);
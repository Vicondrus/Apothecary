import React, {Component} from "react";
import MedicationPlanService from "../../services/medication-plan.service";
import {Link, Redirect} from "react-router-dom";
import {connect} from "react-redux";

class MedicationPlanList extends Component {
    constructor(props) {
        super(props);
        this.onChangeSearchName = this.onChangeSearchName.bind(this);
        this.retrieveMedicationPlans = this.retrieveMedicationPlans.bind(this);
        this.refreshList = this.refreshList.bind(this);
        this.setActiveMedicationPlan = this.setActiveMedicationPlan.bind(this);
        this.searchName = this.searchName.bind(this);

        this.state = {
            medicationPlans: [],
            currentMedicationPlan: null,
            currentIndex: -1,
            searchName: "",
            patientId: null,
        };
    }

    componentDidMount() {
        const id = this.props.match.params.patientId;
        this.setState({
            patientId: id
        });
        this.retrieveMedicationPlans(id);
    }

    onChangeSearchName(e) {
        const searchName = e.target.value;

        this.setState({
            searchName: searchName
        });
    }

    retrieveMedicationPlans(id) {
        const {user: currentUser} = this.props;

        if (!currentUser.roles.includes("PATIENT")) {
            MedicationPlanService.getAllMedicationPlans(id)
                .then(response => {
                    this.setState({
                        medicationPlans: response.data
                    });
                    console.log(response.data);
                })
                .catch(e => {
                    console.log(e);
                });
        } else {
            MedicationPlanService.getAllMedicationPlansForLoggedPatient()
                .then(response => {
                    this.setState({
                        medicationPlans: response.data
                    });
                    console.log(response.data);
                })
                .catch(e => {
                    console.log(e);
                });
        }
    }

    refreshList() {
        this.retrieveMedicationPlans();
        this.setState({
            currentMedicationPlan: null,
            currentIndex: -1
        });
    }

    setActiveMedicationPlan(medicationPlan, index) {
        this.setState({
            currentMedicationPlan: medicationPlan,
            currentIndex: index
        });
    }

    searchName() {
        MedicationPlanService.getByName(this.state.searchName)
            .then(response => {
                this.setState({
                    medicationPlans: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {searchName, medicationPlans, currentMedicationPlan, currentIndex} = this.state;
        const {user: currentUser} = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }

        return (
            <div className="list row">
                <div className="col-md-8">
                    <div className="input-group mb-3">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Search by name"
                            value={searchName}
                            onChange={this.onChangeSearchName}
                        />
                        <div className="input-group-append">
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={this.searchName}
                            >
                                Search
                            </button>
                        </div>
                    </div>
                </div>
                <div className="col-md-6">
                    <h4>MedicationPlans List</h4>

                    <ul className="list-group">
                        {medicationPlans &&
                        medicationPlans.map((medicationPlan, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveMedicationPlan(medicationPlan, index)}
                                key={index}
                            >
                                {medicationPlan.periodStart.split("T")[0]} - {medicationPlan.periodEnd.split("T")[0]} Treatment
                            </li>
                        ))}
                    </ul>

                    {currentUser.roles.includes("DOCTOR") ? (
                        <Link to={'/create-medication-plan/' + this.state.patientId}>
                            <button className="m-3 btn btn-sm btn-success">
                                Create MedicationPlan
                            </button>
                        </Link>) : (<br/>)}

                </div>
                <div className="col-md-6">
                    {currentMedicationPlan ? (
                        <div>
                            <h4>MedicationPlan</h4>
                            <ul className="list-group">
                                {
                                    currentMedicationPlan.medications.map((entry, index) => (
                                        <li
                                            className="list-group-item-secondary"
                                            key={index}
                                        >
                                            {entry.medication.name} to be taken every {entry.intakeInterval} hours
                                        </li>
                                    ))}
                            </ul>

                        </div>
                    ) : (
                        <div>
                            <br/>
                            <p>Please click on a MedicationPlan...</p>
                        </div>
                    )}
                </div>
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

export default connect(mapStateToProps)(MedicationPlanList);
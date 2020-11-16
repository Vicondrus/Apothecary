import React, {Component} from "react";
import MedicationService from "../../services/medication.service";
import {Link, Redirect} from "react-router-dom";
import {connect} from "react-redux";

class MedicationList extends Component {
    constructor(props) {
        super(props);
        this.onChangeSearchName = this.onChangeSearchName.bind(this);
        this.retrieveMedications = this.retrieveMedications.bind(this);
        this.refreshList = this.refreshList.bind(this);
        this.setActiveMedication = this.setActiveMedication.bind(this);
        this.searchName = this.searchName.bind(this);

        this.state = {
            medications: [],
            currentMedication: null,
            currentIndex: -1,
            searchName: "",
        };
    }

    componentDidMount() {
        this.retrieveMedications();
    }

    onChangeSearchName(e) {
        const searchName = e.target.value;

        this.setState({
            searchName: searchName
        });
    }

    retrieveMedications() {
        MedicationService.getAllMedications()
            .then(response => {
                this.setState({
                    medications: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    refreshList() {
        this.retrieveMedications();
        this.setState({
            currentMedication: null,
            currentIndex: -1
        });
    }

    setActiveMedication(medication, index) {
        this.setState({
            currentMedication: medication,
            currentIndex: index
        });
    }

    searchName() {
        MedicationService.getByName(this.state.searchName)
            .then(response => {
                this.setState({
                    medications: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {searchName, medications, currentMedication, currentIndex} = this.state;
        const {user: currentUser} = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }

        if (!currentUser.roles.includes("DOCTOR")) {
            return <Redirect to="/home"/>;
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
                    <h4>Medications List</h4>

                    <ul className="list-group">
                        {medications &&
                        medications.map((medication, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveMedication(medication, index)}
                                key={index}
                            >
                                {medication.name}
                            </li>
                        ))}
                    </ul>

                    <Link to={'/create-medication'}>
                        <button className="m-3 btn btn-sm btn-success">
                            Create Medication
                        </button>
                    </Link>
                </div>
                <div className="col-md-6">
                    {currentMedication ? (
                        <div>
                            <h4>Medication</h4>
                            <div>
                                <label>
                                    <strong>Name:</strong>
                                </label>{" "}
                                {currentMedication.name}
                            </div>
                            <div>
                                <label>
                                    <strong>Side Effects:</strong>
                                </label>{" "}
                                {currentMedication.sideEffects}
                            </div>
                            <div>
                                <label>
                                    <strong>Dosage:</strong>
                                </label>{" "}
                                {currentMedication.dosage + " " + currentMedication.dosageUnits}
                            </div>

                            <Link
                                to={"/medications/" + currentMedication.id}
                                className="badge badge-warning"
                            >
                                Edit
                            </Link>
                        </div>
                    ) : (
                        <div>
                            <br/>
                            <p>Please click on a Medication...</p>
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

export default connect(mapStateToProps)(MedicationList);
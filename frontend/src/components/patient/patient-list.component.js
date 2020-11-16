import React, {Component} from "react";
import PatientService from "../../services/patient.service";
import {Link, Redirect} from "react-router-dom";
import {connect} from "react-redux";

class PatientList extends Component {
    constructor(props) {
        super(props);
        this.onChangeSearchName = this.onChangeSearchName.bind(this);
        this.retrievePatients = this.retrievePatients.bind(this);
        this.refreshList = this.refreshList.bind(this);
        this.setActivePatient = this.setActivePatient.bind(this);
        this.searchName = this.searchName.bind(this);

        this.state = {
            patients: [],
            currentPatient: null,
            currentIndex: -1,
            searchName: ""
        };
    }

    componentDidMount() {
        this.retrievePatients();
    }

    onChangeSearchName(e) {
        const searchName = e.target.value;

        this.setState({
            searchName: searchName
        });
    }

    retrievePatients() {
        PatientService.getAllPatients()
            .then(response => {
                this.setState({
                    patients: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    refreshList() {
        this.retrievePatients();
        this.setState({
            currentPatient: null,
            currentIndex: -1
        });
    }

    setActivePatient(patient, index) {
        this.setState({
            currentPatient: patient,
            currentIndex: index
        });
    }

    searchName() {
        PatientService.getByName(this.state.searchName)
            .then(response => {
                this.setState({
                    patients: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {searchName, patients, currentPatient, currentIndex} = this.state;
        const {user: currentUser} = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }
        if (!currentUser.roles.includes("DOCTOR") && !currentUser.roles.includes("CAREGIVER")) {
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
                    <h4>Patients List</h4>

                    <ul className="list-group">
                        {patients &&
                        patients.map((patient, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActivePatient(patient, index)}
                                key={index}
                            >
                                {patient.firstName} {patient.lastName}
                            </li>
                        ))}
                    </ul>

                    {currentUser.roles.includes("DOCTOR") ? (
                        <Link to={'/create-patient'}>
                            <button className="m-3 btn btn-sm btn-success">
                                Create Patient
                            </button>
                        </Link>) : (<br/>)}
                </div>
                <div className="col-md-6">
                    {currentPatient ? (
                        <div>
                            <h4><strong>Patient</strong></h4>
                            <div>
                                <label>
                                    <strong>Username:</strong>
                                </label>{" "}
                                {currentPatient.username}
                            </div>
                            <div>
                                <label>
                                    <strong>Name:</strong>
                                </label>{" "}
                                {currentPatient.firstName} {currentPatient.lastName}
                            </div>
                            <div>
                                <label>
                                    <strong>Birthdate:</strong>
                                </label>{" "}
                                {currentPatient.birthDate.split("T")[0]}
                            </div>
                            <div>
                                <label>
                                    <strong>Gender:</strong>
                                </label>{" "}
                                {currentPatient.gender.toLowerCase()}
                            </div>
                            <div>
                                <label>
                                    <strong>Caregiver:</strong>
                                </label>{" "}
                                {currentPatient.caregiver.firstName} {currentPatient.caregiver.lastName}
                            </div>
                            <br/>
                            <h5><strong>Address</strong></h5>
                            <div>
                                <label>
                                    <strong>Street:</strong>
                                </label>{" "}
                                {currentPatient.address.street} No.{currentPatient.address.number}
                            </div>
                            <div>
                                <label>
                                    <strong>City:</strong>
                                </label>{" "}
                                {currentPatient.address.city}
                            </div>
                            <div>
                                <label>
                                    <strong>Country:</strong>
                                </label>{" "}
                                {currentPatient.address.country}
                            </div>

                            <Link
                                to={"/patients/" + currentPatient.id}
                                className="badge badge-warning"
                            >
                                Edit
                            </Link>
                            <Link
                                to={"/patients/" + currentPatient.id + "/medication-plans"}
                                className="badge badge-info"
                            >
                                Medication Plans
                            </Link>
                        </div>
                    ) : (
                        <div>
                            <br/>
                            <p>Please click on a Patient...</p>
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

export default connect(mapStateToProps)(PatientList);
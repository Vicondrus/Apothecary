import React, {Component} from "react";
import DoctorService from "../../services/doctor.service";
import {Link, Redirect} from "react-router-dom";
import {connect} from "react-redux";

class DoctorList extends Component {
    constructor(props) {
        super(props);
        this.onChangeSearchName = this.onChangeSearchName.bind(this);
        this.retrieveDoctors = this.retrieveDoctors.bind(this);
        this.refreshList = this.refreshList.bind(this);
        this.setActiveDoctor = this.setActiveDoctor.bind(this);
        this.searchName = this.searchName.bind(this);

        this.state = {
            doctors: [],
            currentDoctor: null,
            currentIndex: -1,
            searchName: ""
        };
    }

    componentDidMount() {
        this.retrieveDoctors();
    }

    onChangeSearchName(e) {
        const searchName = e.target.value;

        this.setState({
            searchName: searchName
        });
    }

    retrieveDoctors() {
        DoctorService.getAllDoctors()
            .then(response => {
                this.setState({
                    doctors: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    refreshList() {
        this.retrieveDoctors();
        this.setState({
            currentDoctor: null,
            currentIndex: -1
        });
    }

    setActiveDoctor(doctor, index) {
        this.setState({
            currentDoctor: doctor,
            currentIndex: index
        });
    }

    searchName() {
        DoctorService.getByName(this.state.searchName)
            .then(response => {
                this.setState({
                    doctors: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {searchName, doctors, currentDoctor, currentIndex} = this.state;
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
                    <h4>Doctors List</h4>

                    <ul className="list-group">
                        {doctors &&
                        doctors.map((doctor, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveDoctor(doctor, index)}
                                key={index}
                            >
                                {doctor.firstName} {doctor.lastName}
                            </li>
                        ))}
                    </ul>

                    <Link to={'/create-doctor'}>
                        <button className="m-3 btn btn-sm btn-success">
                            Create Doctor
                        </button>
                    </Link>
                </div>
                <div className="col-md-6">
                    {currentDoctor ? (
                        <div>
                            <h4><strong>Doctor</strong></h4>
                            <div>
                                <label>
                                    <strong>Username:</strong>
                                </label>{" "}
                                {currentDoctor.username}
                            </div>
                            <div>
                                <label>
                                    <strong>Name:</strong>
                                </label>{" "}
                                {currentDoctor.firstName} {currentDoctor.lastName}
                            </div>
                            <div>
                                <label>
                                    <strong>Birthdate:</strong>
                                </label>{" "}
                                {currentDoctor.birthDate.split("T")[0]}
                            </div>
                            <div>
                                <label>
                                    <strong>Gender:</strong>
                                </label>{" "}
                                {currentDoctor.gender.toLowerCase()}
                            </div>
                            <br/>
                            <h5><strong>Address</strong></h5>
                            <div>
                                <label>
                                    <strong>Street:</strong>
                                </label>{" "}
                                {currentDoctor.address.street} No.{currentDoctor.address.number}
                            </div>
                            <div>
                                <label>
                                    <strong>City:</strong>
                                </label>{" "}
                                {currentDoctor.address.city}
                            </div>
                            <div>
                                <label>
                                    <strong>Country:</strong>
                                </label>{" "}
                                {currentDoctor.address.country}
                            </div>

                            <Link
                                to={"/doctors/" + currentDoctor.id}
                                className="badge badge-warning"
                            >
                                Edit
                            </Link>
                        </div>
                    ) : (
                        <div>
                            <br/>
                            <p>Please click on a Doctor...</p>
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

export default connect(mapStateToProps)(DoctorList);
import React, {Component} from "react";
import CaregiverService from "../../services/caregiver.service";
import {Link} from "react-router-dom";

export default class CaregiverList extends Component {
    constructor(props) {
        super(props);
        this.onChangeSearchName = this.onChangeSearchName.bind(this);
        this.retrieveCaregivers = this.retrieveCaregivers.bind(this);
        this.refreshList = this.refreshList.bind(this);
        this.setActiveCaregiver = this.setActiveCaregiver.bind(this);
        this.searchName = this.searchName.bind(this);

        this.state = {
            caregivers: [],
            currentCaregiver: null,
            currentIndex: -1,
            searchName: ""
        };
    }

    componentDidMount() {
        this.retrieveCaregivers();
    }

    onChangeSearchName(e) {
        const searchName = e.target.value;

        this.setState({
            searchName: searchName
        });
    }

    retrieveCaregivers() {
        CaregiverService.getAllCaregivers()
            .then(response => {
                this.setState({
                    caregivers: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    refreshList() {
        this.retrieveCaregivers();
        this.setState({
            currentCaregiver: null,
            currentIndex: -1
        });
    }

    setActiveCaregiver(caregiver, index) {
        this.setState({
            currentCaregiver: caregiver,
            currentIndex: index
        });
    }

    searchName() {
        CaregiverService.getByName(this.state.searchName)
            .then(response => {
                this.setState({
                    caregivers: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {searchName, caregivers, currentCaregiver, currentIndex} = this.state;

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
                    <h4>Caregivers List</h4>

                    <ul className="list-group">
                        {caregivers &&
                        caregivers.map((caregiver, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveCaregiver(caregiver, index)}
                                key={index}
                            >
                                {caregiver.firstName} {caregiver.lastName}
                            </li>
                        ))}
                    </ul>

                    <Link to={'/create-caregiver'}>
                        <button className="m-3 btn btn-sm btn-success">
                            Create Caregiver
                        </button>
                    </Link>
                </div>
                <div className="col-md-6">
                    {currentCaregiver ? (
                        <div>
                            <h4><strong>Caregiver</strong></h4>
                            <div>
                                <label>
                                    <strong>Username:</strong>
                                </label>{" "}
                                {currentCaregiver.username}
                            </div>
                            <div>
                                <label>
                                    <strong>Name:</strong>
                                </label>{" "}
                                {currentCaregiver.firstName} {currentCaregiver.lastName}
                            </div>
                            <div>
                                <label>
                                    <strong>Birthdate:</strong>
                                </label>{" "}
                                {currentCaregiver.birthDate.split("T")[0]}
                            </div>
                            <div>
                                <label>
                                    <strong>Gender:</strong>
                                </label>{" "}
                                {currentCaregiver.gender.toLowerCase()}
                            </div>
                            <br/>
                            <h5><strong>Address</strong></h5>
                            <div>
                                <label>
                                    <strong>Street:</strong>
                                </label>{" "}
                                {currentCaregiver.address.street} No.{currentCaregiver.address.number}
                            </div>
                            <div>
                                <label>
                                    <strong>City:</strong>
                                </label>{" "}
                                {currentCaregiver.address.city}
                            </div>
                            <div>
                                <label>
                                    <strong>Country:</strong>
                                </label>{" "}
                                {currentCaregiver.address.country}
                            </div>

                            <Link
                                to={"/caregivers/" + currentCaregiver.id}
                                className="badge badge-warning"
                            >
                                Edit
                            </Link>
                        </div>
                    ) : (
                        <div>
                            <br/>
                            <p>Please click on a Caregiver...</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

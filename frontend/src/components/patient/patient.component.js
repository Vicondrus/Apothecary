import React, {Component} from "react";
import {Link, Redirect} from "react-router-dom";
import PatientService from "../../services/patient.service";
import AddressService from "../../services/address.service";
import CaregiverService from "../../services/caregiver.service";
import {connect} from "react-redux";

class Patient extends Component {
    constructor(props) {
        super(props);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
        this.onChangeFirstName = this.onChangeFirstName.bind(this);
        this.onChangeAddressDrop = this.onChangeAddressDrop.bind(this);
        this.onChangeLastName = this.onChangeLastName.bind(this);
        this.onChangeBirthDate = this.onChangeBirthDate.bind(this);
        this.onChangeGender = this.onChangeGender.bind(this);
        this.onChangeAddressNumber = this.onChangeAddressNumber.bind(this);
        this.onChangeAddressStreet = this.onChangeAddressStreet.bind(this);
        this.onChangeAddressCity = this.onChangeAddressCity.bind(this);
        this.onChangeAddressCountry = this.onChangeAddressCountry.bind(this);
        this.getPatient = this.getPatient.bind(this);
        this.updatePatient = this.updatePatient.bind(this);
        this.deletePatient = this.deletePatient.bind(this);
        this.populateAddresses = this.populateAddresses.bind(this);
        this.populateCaregivers = this.populateCaregivers.bind(this);
        this.onChangeCaregiverDrop = this.onChangeCaregiverDrop.bind(this);

        this.state = {
            error: false,
            currentPatient: {
                id: null,
                username: "",
                password: "",
                firstName: "",
                lastName: "",
                birthDate: "",
                gender: null,
                address: {
                    id: "-1",
                    number: null,
                    street: "",
                    city: "",
                    country: ""
                },
                caregiver: {
                    id: ""
                }
            },
            message: "",
            addresses: [],
            caregivers: []
        };
    }

    componentDidMount() {
        this.getPatient(this.props.match.params.id);

        this.populateAddresses();
        this.populateCaregivers();
    }

    populateCaregivers() {
        CaregiverService.getAllCaregivers()
            .then((response) => {
                return response.data;
            })
            .then((data) => {
                let caregiversFromApi = data.map(caregiver => {
                    return {value: caregiver.id, display: `${caregiver.firstName} ${caregiver.lastName}`}
                });
                this.setState({
                    caregivers: [{value: "", display: 'Select Caregiver'}].concat(caregiversFromApi)
                });
            }).catch(error => {
            console.log(error);
        });
    }

    populateAddresses() {
        AddressService.getAllAddresses()
            .then((response) => {
                return response.data;
            })
            .then((data) => {
                let addressesFromApi = data.map(address => {
                    return {value: address.id, display: `${address.street} no.${address.number}`}
                });
                this.setState({
                    addresses: [{value: -1, display: 'New Address (Completed Below)'}].concat(addressesFromApi)
                });
            }).catch(error => {
            console.log(error);
        });
    }

    onChangeUsername(e) {
        const username = e.target.value;

        this.setState(function (prevState) {
            return {
                currentPatient: {
                    ...prevState.currentPatient,
                    username: username
                }
            };
        });
    }

    onChangeCaregiverDrop(e) {
        const id = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                caregiver: {
                    ...prevState.currentPatient.caregiver,
                    id: id
                }
            }
        }));
    }

    onChangePassword(e) {
        const password = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                password: password
            }
        }));
    }

    onChangeAddressNumber(e) {
        const number = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                address: {
                    ...prevState.currentPatient.address,
                    number: number
                }
            }
        }));
    }

    onChangeAddressStreet(e) {
        const street = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                address: {
                    ...prevState.currentPatient.address,
                    street: street
                }
            }
        }));
    }

    onChangeAddressCity(e) {
        const city = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                address: {
                    ...prevState.currentPatient.address,
                    city: city
                }
            }
        }));
    }

    onChangeAddressCountry(e) {
        const country = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                address: {
                    ...prevState.currentPatient.address,
                    country: country
                }
            }
        }));
    }

    onChangeAddressDrop(e) {
        const id = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                address: {
                    ...prevState.currentPatient.address,
                    id: id
                }
            }
        }));
    }

    onChangeFirstName(e) {
        const firstName = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                firstName: firstName
            }
        }));
    }

    onChangeLastName(e) {
        const lastName = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                lastName: lastName
            }
        }));
    }

    onChangeBirthDate(e) {
        const birthDate = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                birthDate: birthDate
            }
        }));
    }

    onChangeGender(e) {
        const gender = e.target.value;

        this.setState(prevState => ({
            currentPatient: {
                ...prevState.currentPatient,
                gender: gender
            }
        }));
    }


    getPatient(id) {
        PatientService.getPatientById(id)
            .then(response => {
                this.setState({
                    currentPatient: response.data
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

    updatePatient() {
        if (this.state.currentPatient.address.id === "-1" || this.state.currentPatient.address.id === -1) {
            delete this.state.currentPatient.address.id
        }
        if (this.state.currentPatient.caregiver.id === "") {
            delete this.state.currentPatient.caregiver
        }
        if (this.state.currentPatient.password === "") {
            delete this.state.currentPatient.password
        }
        PatientService.updatePatient(
            this.state.currentPatient.id,
            this.state.currentPatient
        )
            .then(response => {
                console.log(response.data);
                this.populateAddresses();
                this.populateCaregivers();
                this.setState({
                    currentPatient: response.data,
                    message: "The patient was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    error: true
                })
            });
    }

    deletePatient() {
        PatientService.deletePatient(this.state.currentPatient.id)
            .then(response => {
                console.log(response.data);
                this.props.history.push('/patients')
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    error: true
                })
            });
    }

    render() {
        const {currentPatient} = this.state;
        const {user: currentUser} = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }

        if (!currentUser.roles.includes("DOCTOR")) {
            return <Redirect to="/home"/>;
        }

        if (this.state.error) {
            return <Redirect to="/error"/>;
        }

        return (
            <div>
                {currentPatient ? (
                    <div className="edit-form">
                        <h4>Patient</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="username">Username</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="username"
                                    required
                                    value={this.state.currentPatient.username}
                                    onChange={this.onChangeUsername}
                                    name="name"
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">Password</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    id="password"
                                    required
                                    value={this.state.currentPatient.password}
                                    onChange={this.onChangePassword}
                                    name="password"
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="firstName">First Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="firstName"
                                    required
                                    value={this.state.currentPatient.firstName}
                                    onChange={this.onChangeFirstName}
                                    name="firstName"
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="dosageUnits">Last Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="lastName"
                                    required
                                    value={this.state.currentPatient.lastName}
                                    onChange={this.onChangeLastName}
                                    name="lastName"
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="birthDate">Birth Date</label>
                                <input
                                    type="date"
                                    className="form-control"
                                    id="birthDate"
                                    required
                                    value={this.state.currentPatient.birthDate.split("T")[0]}
                                    onChange={this.onChangeBirthDate}
                                    name="birthDate"
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="gender">Gender</label>
                                <select id="gender" name="gender" className="form-control"
                                        onChange={this.onChangeGender}
                                        value={this.state.currentPatient.gender}>
                                    <option value="">Choose a gender</option>
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                    <option value="OTHER">Other</option>
                                </select>
                            </div>

                            <div className="form-group">
                                <label htmlFor="caregiver">Caregiver</label>
                                <select id="caregiver" name="caregiver" className="form-control"
                                        onChange={this.onChangeCaregiverDrop}
                                        value={this.state.currentPatient.caregiver.id}>
                                    {this.state.caregivers.map((caregiver) => <option key={caregiver.value}
                                                                                      value={caregiver.value}>{caregiver.display}</option>)}
                                </select>
                            </div>

                            <div className="form-group">
                                <label htmlFor="address">Address</label>
                                <select id="address" name="address" className="form-control"
                                        onChange={this.onChangeAddressDrop}
                                        value={this.state.currentPatient.address.id}>
                                    {this.state.addresses.map((address) => <option key={address.value}
                                                                                   value={address.value}>{address.display}</option>)}
                                </select>
                            </div>

                            {this.state.currentPatient.address.id === -1 || this.state.currentPatient.address.id === "-1" ? (
                                <div>
                                    <h3>Address</h3>

                                    <div className="form-group">
                                        <label htmlFor="number">Number</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="number"
                                            required
                                            value={this.state.currentPatient.address.number}
                                            onChange={this.onChangeAddressNumber}
                                            name="number"
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="street">Street</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="street"
                                            required
                                            value={this.state.currentPatient.address.street}
                                            onChange={this.onChangeAddressStreet}
                                            name="street"
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="city">City</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="city"
                                            required
                                            value={this.state.currentPatient.address.city}
                                            onChange={this.onChangeAddressCity}
                                            name="city"
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="country">Country</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="country"
                                            required
                                            value={this.state.currentPatient.address.country}
                                            onChange={this.onChangeAddressCountry}
                                            name="country"
                                        />
                                    </div>
                                </div>) : (<div>
                                <br/>
                                <p>Existing address selected.</p>
                            </div>)

                            }
                        </form>

                        <button
                            className="badge badge-danger mr-2"
                            onClick={this.deletePatient}
                        >
                            Delete
                        </button>

                        <button
                            type="submit"
                            className="badge badge-success"
                            onClick={this.updatePatient}
                        >
                            Update
                        </button>

                        <Link to={'/patients'}>
                            <button className="badge badge-primary mr-2">
                                Back
                            </button>
                        </Link>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br/>
                        <p>Please click on a Patient...</p>
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

export default connect(mapStateToProps)(Patient);
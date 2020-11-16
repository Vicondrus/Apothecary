import React, {Component} from "react";
import {Link, Redirect} from "react-router-dom";
import DoctorService from "../../services/doctor.service";
import AddressService from "../../services/address.service";
import {connect} from "react-redux";

class Doctor extends Component {
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
        this.getDoctor = this.getDoctor.bind(this);
        this.updateDoctor = this.updateDoctor.bind(this);
        this.deleteDoctor = this.deleteDoctor.bind(this);
        this.populateAddresses = this.populateAddresses.bind(this);

        this.state = {
            currentDoctor: {
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
                }
            },
            message: "",
            addresses: [],
            error: false
        };
    }

    componentDidMount() {
        this.getDoctor(this.props.match.params.id);

        this.populateAddresses();
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
                currentDoctor: {
                    ...prevState.currentDoctor,
                    username: username
                }
            };
        });
    }

    onChangePassword(e) {
        const password = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                password: password
            }
        }));
    }

    onChangeAddressNumber(e) {
        const number = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                address: {
                    ...prevState.currentDoctor.address,
                    number: number
                }
            }
        }));
    }

    onChangeAddressStreet(e) {
        const street = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                address: {
                    ...prevState.currentDoctor.address,
                    street: street
                }
            }
        }));
    }

    onChangeAddressCity(e) {
        const city = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                address: {
                    ...prevState.currentDoctor.address,
                    city: city
                }
            }
        }));
    }

    onChangeAddressCountry(e) {
        const country = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                address: {
                    ...prevState.currentDoctor.address,
                    country: country
                }
            }
        }));
    }

    onChangeAddressDrop(e) {
        const id = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                address: {
                    ...prevState.currentDoctor.address,
                    id: id
                }
            }
        }));
    }

    onChangeFirstName(e) {
        const firstName = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                firstName: firstName
            }
        }));
    }

    onChangeLastName(e) {
        const lastName = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                lastName: lastName
            }
        }));
    }

    onChangeBirthDate(e) {
        const birthDate = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                birthDate: birthDate
            }
        }));
    }

    onChangeGender(e) {
        const gender = e.target.value;

        this.setState(prevState => ({
            currentDoctor: {
                ...prevState.currentDoctor,
                gender: gender
            }
        }));
    }


    getDoctor(id) {
        DoctorService.getDoctorById(id)
            .then(response => {
                this.setState({
                    currentDoctor: response.data
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

    updateDoctor() {
        if (this.state.currentDoctor.address.id === "-1" || this.state.currentDoctor.address.id === -1) {
            delete this.state.currentDoctor.address.id
        }
        if (this.state.currentDoctor.password === "") {
            delete this.state.currentDoctor.password
        }
        DoctorService.updateDoctor(
            this.state.currentDoctor.id,
            this.state.currentDoctor
        )
            .then(response => {
                console.log(response.data);
                this.populateAddresses();
                this.setState({
                    currentDoctor: response.data,
                    message: "The doctor was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    error: true
                })
            });
    }

    deleteDoctor() {
        DoctorService.deleteDoctor(this.state.currentDoctor.id)
            .then(response => {
                console.log(response.data);
                this.props.history.push('/doctors')
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    error: true
                })
            });
    }

    render() {
        const {currentDoctor} = this.state;
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
            <div>
                {currentDoctor ? (
                    <div className="edit-form">
                        <h4>Doctor</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="username">Username</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="username"
                                    required
                                    value={this.state.currentDoctor.username}
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
                                    value={this.state.currentDoctor.password}
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
                                    value={this.state.currentDoctor.firstName}
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
                                    value={this.state.currentDoctor.lastName}
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
                                    value={this.state.currentDoctor.birthDate.split("T")[0]}
                                    onChange={this.onChangeBirthDate}
                                    name="birthDate"
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="gender">Gender</label>
                                <select id="gender" name="gender" className="form-control"
                                        onChange={this.onChangeGender}
                                        value={this.state.currentDoctor.gender === "MALE" ? 0 : this.state.currentDoctor.gender === "FEMALE" ? 1 : 2}>
                                    <option value="">Choose a gender</option>
                                    <option value="0">Male</option>
                                    <option value="1">Female</option>
                                    <option value="2">Other</option>
                                </select>
                            </div>

                            <div className="form-group">
                                <label htmlFor="address">Address</label>
                                <select id="address" name="address" className="form-control"
                                        onChange={this.onChangeAddressDrop}
                                        value={this.state.currentDoctor.address.id}>
                                    {this.state.addresses.map((address) => <option key={address.value}
                                                                                   value={address.value}>{address.display}</option>)}
                                </select>
                            </div>

                            {this.state.currentDoctor.address.id === -1 || this.state.currentDoctor.address.id === "-1" ? (
                                <div>
                                    <h3>Address</h3>

                                    <div className="form-group">
                                        <label htmlFor="number">Number</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="number"
                                            required
                                            value={this.state.currentDoctor.address.number}
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
                                            value={this.state.currentDoctor.address.street}
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
                                            value={this.state.currentDoctor.address.city}
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
                                            value={this.state.currentDoctor.address.country}
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
                            onClick={this.deleteDoctor}
                        >
                            Delete
                        </button>

                        <button
                            type="submit"
                            className="badge badge-success"
                            onClick={this.updateDoctor}
                        >
                            Update
                        </button>

                        <Link to={'/doctors'}>
                            <button className="badge badge-primary mr-2">
                                Back
                            </button>
                        </Link>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br/>
                        <p>Please click on a Doctor...</p>
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

export default connect(mapStateToProps)(Doctor);
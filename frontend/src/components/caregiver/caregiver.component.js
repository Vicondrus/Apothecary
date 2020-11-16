import React, {Component} from "react";
import {Link, Redirect} from "react-router-dom";
import CaregiverService from "../../services/caregiver.service";
import AddressService from "../../services/address.service";

export default class Caregiver extends Component {
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
        this.getCaregiver = this.getCaregiver.bind(this);
        this.updateCaregiver = this.updateCaregiver.bind(this);
        this.deleteCaregiver = this.deleteCaregiver.bind(this);
        this.populateAddresses = this.populateAddresses.bind(this);

        this.state = {
            currentCaregiver: {
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
        this.getCaregiver(this.props.match.params.id);

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
                currentCaregiver: {
                    ...prevState.currentCaregiver,
                    username: username
                }
            };
        });
    }

    onChangePassword(e) {
        const password = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                password: password
            }
        }));
    }

    onChangeAddressNumber(e) {
        const number = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                address: {
                    ...prevState.currentCaregiver.address,
                    number: number
                }
            }
        }));
    }

    onChangeAddressStreet(e) {
        const street = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                address: {
                    ...prevState.currentCaregiver.address,
                    street: street
                }
            }
        }));
    }

    onChangeAddressCity(e) {
        const city = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                address: {
                    ...prevState.currentCaregiver.address,
                    city: city
                }
            }
        }));
    }

    onChangeAddressCountry(e) {
        const country = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                address: {
                    ...prevState.currentCaregiver.address,
                    country: country
                }
            }
        }));
    }

    onChangeAddressDrop(e) {
        const id = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                address: {
                    ...prevState.currentCaregiver.address,
                    id: id
                }
            }
        }));
    }

    onChangeFirstName(e) {
        const firstName = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                firstName: firstName
            }
        }));
    }

    onChangeLastName(e) {
        const lastName = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                lastName: lastName
            }
        }));
    }

    onChangeBirthDate(e) {
        const birthDate = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                birthDate: birthDate
            }
        }));
    }

    onChangeGender(e) {
        const gender = e.target.value;

        this.setState(prevState => ({
            currentCaregiver: {
                ...prevState.currentCaregiver,
                gender: gender
            }
        }));
    }


    getCaregiver(id) {
        CaregiverService.getCaregiverById(id)
            .then(response => {
                this.setState({
                    currentCaregiver: response.data
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

    updateCaregiver() {
        if (this.state.currentCaregiver.address.id === "-1" || this.state.currentCaregiver.address.id === -1) {
            delete this.state.currentCaregiver.address.id
        }
        if (this.state.currentCaregiver.password === "") {
            delete this.state.currentCaregiver.password
        }
        CaregiverService.updateCaregiver(
            this.state.currentCaregiver.id,
            this.state.currentCaregiver
        )
            .then(response => {
                console.log(response.data);
                this.populateAddresses();
                this.setState({
                    currentCaregiver: response.data,
                    message: "The caregiver was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    error: true
                })
            });
    }

    deleteCaregiver() {
        CaregiverService.deleteCaregiver(this.state.currentCaregiver.id)
            .then(response => {
                console.log(response.data);
                this.props.history.push('/caregivers')
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    error: true
                })
            });
    }

    render() {
        const {currentCaregiver} = this.state;

        if (this.state.error) {
            return <Redirect to="/error"/>
        }

        return (
            <div>
                {currentCaregiver ? (
                    <div className="edit-form">
                        <h4>Caregiver</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="username">Username</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="username"
                                    required
                                    value={this.state.currentCaregiver.username}
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
                                    value={this.state.currentCaregiver.password}
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
                                    value={this.state.currentCaregiver.firstName}
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
                                    value={this.state.currentCaregiver.lastName}
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
                                    value={this.state.currentCaregiver.birthDate.split("T")[0]}
                                    onChange={this.onChangeBirthDate}
                                    name="birthDate"
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="gender">Gender</label>
                                <select id="gender" name="gender" className="form-control"
                                        onChange={this.onChangeGender}
                                        value={this.state.currentCaregiver.gender === "MALE" ? 0 : this.state.currentCaregiver.gender === "FEMALE" ? 1 : 2}>
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
                                        value={this.state.currentCaregiver.address.id}>
                                    {this.state.addresses.map((address) => <option key={address.value}
                                                                                   value={address.value}>{address.display}</option>)}
                                </select>
                            </div>

                            {this.state.currentCaregiver.address.id === -1 || this.state.currentCaregiver.address.id === "-1" ? (
                                <div>
                                    <h3>Address</h3>

                                    <div className="form-group">
                                        <label htmlFor="number">Number</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="number"
                                            required
                                            value={this.state.currentCaregiver.address.number}
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
                                            value={this.state.currentCaregiver.address.street}
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
                                            value={this.state.currentCaregiver.address.city}
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
                                            value={this.state.currentCaregiver.address.country}
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
                            onClick={this.deleteCaregiver}
                        >
                            Delete
                        </button>

                        <button
                            type="submit"
                            className="badge badge-success"
                            onClick={this.updateCaregiver}
                        >
                            Update
                        </button>

                        <Link to={'/caregivers'}>
                            <button className="badge badge-primary mr-2">
                                Back
                            </button>
                        </Link>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br/>
                        <p>Please click on a Caregiver...</p>
                    </div>
                )}
            </div>
        );
    }
}
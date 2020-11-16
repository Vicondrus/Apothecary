import React, {Component} from "react";
import DoctorService from "../../services/doctor.service";
import AddressService from "../../services/address.service";
import {Link, Redirect} from "react-router-dom";
import {connect} from "react-redux";

class CreateDoctor extends Component {
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
        this.saveDoctor = this.saveDoctor.bind(this);
        this.newDoctor = this.newDoctor.bind(this);

        this.state = {
            id: null,
            username: "",
            password: "",
            firstName: "",
            lastName: "",
            birthDate: new Date(Date.now()),
            gender: null,

            addressId: "-1",
            number: null,
            street: "",
            city: "",
            country: "",
            addresses: [],

            submitted: false,
            error: false
        };
    }

    componentDidMount() {
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
        this.setState({
            username: e.target.value
        });
    }

    onChangeAddressNumber(e) {
        this.setState({
            number: e.target.value
        });
    }

    onChangeAddressStreet(e) {
        this.setState({
            street: e.target.value
        });
    }

    onChangeAddressCity(e) {
        this.setState({
            city: e.target.value
        });
    }

    onChangeAddressCountry(e) {
        this.setState({
            country: e.target.value
        });
    }

    onChangeAddressDrop(e) {
        this.setState({
            addressId: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
        });
    }

    onChangeFirstName(e) {
        this.setState({
            firstName: e.target.value
        });
    }

    onChangeLastName(e) {
        this.setState({
            lastName: e.target.value
        });
    }

    onChangeBirthDate(e) {
        this.setState({
            birthDate: e.target.value
        });
    }

    onChangeGender(e) {
        this.setState({
            gender: e.target.value
        });
    }

    saveDoctor() {
        let data = {}

        if (this.state.addressId === "-1") {
            data = {
                username: this.state.username,
                password: this.state.password,
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                birthDate: this.state.birthDate,
                gender: this.state.gender,
                userType: 1,
                address: {
                    number: this.state.number,
                    street: this.state.street,
                    city: this.state.city,
                    country: this.state.country
                }
            };
        } else {
            data = {
                username: this.state.username,
                password: this.state.password,
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                birthDate: this.state.birthDate,
                gender: this.state.gender,
                userType: 1,
                address: {id: this.state.addressId}
            };
        }

        DoctorService.createDoctor(data)
            .then(response => {
                this.setState({
                    id: response.data.id,
                    name: response.data.name,
                    password: response.data.password,
                    firstName: response.data.firstName,
                    lastName: response.data.lastName,
                    birthDate: response.data.birthDate,
                    gender: response.data.gender,
                    addressId: response.data.address.id,
                    number: response.data.address.number,
                    street: response.data.address.street,
                    city: response.data.address.city,
                    country: response.data.address.country,

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

    newDoctor() {
        this.setState({
            id: null,
            username: "",
            password: "",
            firstName: "",
            lastName: "",
            birthDate: new Date(Date.now()),
            gender: null,
            number: null,
            street: "",
            city: "",
            country: "",

            addressId: "-1",

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
                        <button className="btn btn-success" onClick={this.newDoctor}>
                            Add
                        </button>
                        <Link to={'/doctors'}>
                            <button className="m-3 btn btn-primary">
                                Back
                            </button>
                        </Link>
                    </div>
                ) : (
                    <div>
                        <h2>New Doctor</h2>
                        <div className="form-group">
                            <label htmlFor="username">Username</label>
                            <input
                                type="text"
                                className="form-control"
                                id="username"
                                required
                                value={this.state.username}
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
                                value={this.state.password}
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
                                value={this.state.firstName}
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
                                value={this.state.lastName}
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
                                value={this.state.birthDate}
                                onChange={this.onChangeBirthDate}
                                name="birthDate"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="gender">Gender</label>
                            <select id="gender" name="gender" className="form-control" onChange={this.onChangeGender}>
                                <option value="" selected={this.state.gender == null}>Choose a gender</option>
                                <option value="0" selected={this.state.gender === 0}>Male</option>
                                <option value="1" selected={this.state.gender === 1}>Female</option>
                                <option value="2" selected={this.state.gender === 2}>Other</option>
                            </select>
                        </div>

                        <div className="form-group">
                            <label htmlFor="address">Address</label>
                            <select id="address" name="address" className="form-control"
                                    onChange={this.onChangeAddressDrop}>
                                {this.state.addresses.map((address) => <option key={address.value}
                                                                               value={address.value}>{address.display}</option>)}
                            </select>
                        </div>

                        {this.state.addressId === -1 || this.state.addressId === "-1" ? (
                            <div>
                                <h3>Address</h3>

                                <div className="form-group">
                                    <label htmlFor="number">Number</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="number"
                                        required
                                        value={this.state.number}
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
                                        value={this.state.street}
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
                                        value={this.state.city}
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
                                        value={this.state.country}
                                        onChange={this.onChangeAddressCountry}
                                        name="country"
                                    />
                                </div>
                            </div>) : (<div>
                            <br/>
                            <p>Existing address selected.</p>
                        </div>)

                        }

                        <button onClick={this.saveDoctor} className="btn btn-success">
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

export default connect(mapStateToProps)(CreateDoctor);
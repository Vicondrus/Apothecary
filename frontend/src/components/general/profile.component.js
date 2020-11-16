import React, {Component} from "react";
import {Redirect} from 'react-router-dom';
import {connect} from "react-redux";
import InfoService from "../../services/info.service";

class Profile extends Component {
    constructor(props) {
        super(props);
        this.getPatient = this.getPatient.bind(this);

        this.state = {
            error: false,
            currentPatient: {
                id: null,
                username: "",
                password: "",
                firstName: "",
                lastName: "",
                birthDate: "",
                gender: "",
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
            }
        };
    }

    componentDidMount() {
        this.getPatient();
    }

    getPatient() {
        const {user: currentUser} = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }

        if (!currentUser.roles.includes("PATIENT")) {
            return <Redirect to="/home"/>;
        }

        InfoService.getUserInformation(currentUser.id)
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

    render() {
        const {user: currentUser} = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }

        if (this.state.error) {
            return <Redirect to="/error"/>;
        }

        return (
            <div>
                <h4><strong>Patient</strong></h4>
                <div>
                    <label>
                        <strong>Username:</strong>
                    </label>{" "}
                    {this.state.currentPatient.username}
                </div>
                <div>
                    <label>
                        <strong>Name:</strong>
                    </label>{" "}
                    {this.state.currentPatient.firstName} {this.state.currentPatient.lastName}
                </div>
                <div>
                    <label>
                        <strong>Birthdate:</strong>
                    </label>{" "}
                    {this.state.currentPatient.birthDate.split("T")[0]}
                </div>
                <div>
                    <label>
                        <strong>Gender:</strong>
                    </label>{" "}
                    {this.state.currentPatient.gender.toLowerCase()}
                </div>
                <div>
                    <label>
                        <strong>Caregiver:</strong>
                    </label>{" "}
                    {this.state.currentPatient.caregiver.firstName} {this.state.currentPatient.caregiver.lastName}
                </div>
                <br/>
                <h5><strong>Address</strong></h5>
                <div>
                    <label>
                        <strong>Street:</strong>
                    </label>{" "}
                    {this.state.currentPatient.address.street} No.{this.state.currentPatient.address.number}
                </div>
                <div>
                    <label>
                        <strong>City:</strong>
                    </label>{" "}
                    {this.state.currentPatient.address.city}
                </div>
                <div>
                    <label>
                        <strong>Country:</strong>
                    </label>{" "}
                    {this.state.currentPatient.address.country}
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

export default connect(mapStateToProps)(Profile);

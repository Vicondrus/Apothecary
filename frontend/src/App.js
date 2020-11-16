import React, {Component} from "react";
import {connect} from "react-redux";
import SockJsClient from 'react-stomp';
import {Link, Route, Router, Switch} from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import 'react-notifications/lib/notifications.css';
import { NotificationContainer } from 'react-notifications';
import { NotificationManager } from 'react-notifications';

import Login from "./components/general/login.component";
import Home from "./components/general/home.component";
import Profile from "./components/general/profile.component";
import {logout} from "./actions/auth";
import {clearMessage} from "./actions/message";

import {history} from './helpers/history';
import MedicationList from "./components/medication/medication-list.component";
import CreateMedication from "./components/medication/add-medication.component";
import Medication from "./components/medication/medication.component";
import CreateCaregiver from "./components/caregiver/add-caregiver.component";
import CaregiverList from "./components/caregiver/caregiver-list.component";
import Caregiver from "./components/caregiver/caregiver.component";
import CreateDoctor from "./components/doctor/add-doctor.component";
import DoctorList from "./components/doctor/doctor-list.component";
import Doctor from "./components/doctor/doctor.component";
import CreatePatient from "./components/patient/add-patient.component";
import PatientList from "./components/patient/patient-list.component";
import Patient from "./components/patient/patient.component";
import MedicationPlanList from "./components/medication-plans/medication-plan-list.component";
import CreateMedicationPlan from "./components/medication-plans/add-medication-plan.component";
import Error from "./components/general/errorboundary.component"

class App extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            showModeratorBoard: false,
            showAdminBoard: false,
            currentUser: undefined
        };

        history.listen((location) => {
            props.dispatch(clearMessage()); // clear message when changing location
        });
    }

    componentDidMount() {
        const user = this.props.user;

        if (user) {
            this.setState({
                currentUser: user,
                showDoctorBoard: user.roles.includes("DOCTOR"),
                showCaregiverBoard: user.roles.includes("CAREGIVER"),
            });

        //    TODO: add websocket here
        }
    }

    logOut() {
        this.props.dispatch(logout());
    }

    render() {
        const {currentUser, showCaregiverBoard, showDoctorBoard} = this.state;

        return (
            <Router history={history}>
                <div>
                    <nav className="navbar navbar-expand navbar-dark bg-dark">
                        <img src={process.env.PUBLIC_URL + '/apothecary.png'} alt="Apothecary Logo"
                             className="img-responsive"
                             width="35"
                             height="35"/>
                        {currentUser && (
                            <Link to={"/"} className="navbar-brand">
                                Apothecary
                            </Link>)
                        }

                        {!currentUser && (
                            <Link to={"/login"} className="navbar-brand">
                                Apothecary
                            </Link>)
                        }

                        <div className="navbar-nav mr-auto">

                            {showDoctorBoard && (
                                <li className="nav-item">
                                    <Link to={"/doctors"} className="nav-link">
                                        Doctors
                                    </Link>
                                </li>
                            )}

                            {(showCaregiverBoard || showDoctorBoard) && (
                                <li className="nav-item">
                                    <Link to={"/patients"} className="nav-link">
                                        Patients
                                    </Link>
                                </li>
                            )}

                            {currentUser && !(showCaregiverBoard || showDoctorBoard) && (
                                <li className="nav-item">
                                    <Link to={"/profile"} className="nav-link">
                                        Profile
                                    </Link>
                                </li>
                            )}

                            {currentUser && !(showCaregiverBoard || showDoctorBoard) && (
                                <li className="nav-item">
                                    <Link to={"/medication-plans"} className="nav-link">
                                        Medication Plans
                                    </Link>
                                </li>
                            )}

                            {showDoctorBoard && (
                                <li className="nav-item">
                                    <Link to={"/caregivers"} className="nav-link">
                                        Caregivers
                                    </Link>
                                </li>
                            )}

                            {showDoctorBoard && (
                                <li className="nav-item">
                                    <Link to={"/medications"} className="nav-link">
                                        Medications
                                    </Link>
                                </li>
                            )}
                        </div>

                        {currentUser && showCaregiverBoard && <SockJsClient url='https://apothecary-backend.herokuapp.com/websocket-notifications'
                                                      topics={[`/anomalous-activities/${currentUser.username}`]}
                                                      onConnect={() => {
                                                          console.log("connected");
                                                          NotificationManager.success('Connected to live notifications service.', 'Successful!', 5000);
                                                      }}
                                                      onDisconnect={() => {
                                                          console.log("Disconnected");
                                                          NotificationManager.error('Disconnected from the notifications service.', 'Disconnected.', 5000);
                                                      }}
                                                      onMessage={(msg) => {
                                                          var dateObj = new Date((msg.activity.end - msg.activity.start));
                                                          var utcString = dateObj.toUTCString();
                                                          var time = utcString.slice(-12, -4);

                                                          NotificationManager.warning(`Suspect activity detected. ${msg.patient.firstName} ${msg.patient.lastName}
                                                           (id ${msg.patient.id}; username ${msg.patient.username}) had too much ${msg.activity.activity} (${time} hours)`,
                                                              'Warning!', 3600000);
                                                      }}
                                                      ref={(client) => {
                                                          this.clientRef = client
                                                      }}/>
                        }

                        {currentUser ? (
                            <div className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <p className="nav-link">
                                        {currentUser.username}
                                    </p>
                                </li>
                                <li className="nav-item">
                                    <a href="/login" className="nav-link" onClick={this.logOut}>
                                        LogOut
                                    </a>
                                </li>
                            </div>
                        ) : (
                            <div className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link to={"/login"} className="nav-link">
                                        Login
                                    </Link>
                                </li>

                                <li className="nav-item">
                                    <Link to={"/register"} className="nav-link">
                                        Sign Up
                                    </Link>
                                </li>
                            </div>
                        )}
                    </nav>

                    <div className="container mt-3">
                        <Switch>
                            <Route exact path={["/", "/home"]} component={Home}/>
                            <Route exact path="/login" component={Login}/>
                            <Route exact path="/profile" component={Profile}/>
                            <Route exact path="/medications" component={MedicationList}/>
                            <Route exact path="/create-medication" component={CreateMedication}/>
                            <Route path="/medications/:id" component={Medication}/>
                            <Route path="/create-caregiver" component={CreateCaregiver}/>
                            <Route exact path="/caregivers" component={CaregiverList}/>
                            <Route path="/caregivers/:id" component={Caregiver}/>
                            <Route path="/create-doctor" component={CreateDoctor}/>
                            <Route exact path="/doctors" component={DoctorList}/>
                            <Route path="/doctors/:id" component={Doctor}/>
                            <Route path="/create-patient" component={CreatePatient}/>
                            <Route exact path="/patients" component={PatientList}/>
                            <Route path="/patients/:patientId/medication-plans" component={MedicationPlanList}/>
                            <Route path="/patients/:id" component={Patient}/>
                            <Route path="/create-medication-plan/:patientId" component={CreateMedicationPlan}/>
                            <Route exact path="/medication-plans" component={MedicationPlanList}/>
                            <Route exact path="/error" component={Error}/>
                        </Switch>
                    </div>
                </div>
                <NotificationContainer />
            </Router>
        );
    }
}

function mapStateToProps(state) {
    const {user} = state.auth;
    return {
        user,
    };
}

export default connect(mapStateToProps)(App);

import React, {Component} from "react";
import MedicationService from "../../services/medication.service";
import {Link, Redirect} from "react-router-dom";
import {connect} from "react-redux";

class Medication extends Component {
    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeSideEffects = this.onChangeSideEffects.bind(this);
        this.onChangeDosage = this.onChangeDosage.bind(this);
        this.onChangeDosageUnits = this.onChangeDosageUnits.bind(this);
        this.getMedication = this.getMedication.bind(this);
        this.updateMedication = this.updateMedication.bind(this);
        this.deleteMedication = this.deleteMedication.bind(this);

        this.state = {
            currentMedication: {
                id: null,
                name: "",
                sideEffects: "",
                dosage: 0.0,
                dosageUnits: ""
            },
            message: "",
            error: false
        };
    }

    componentDidMount() {
        this.getMedication(this.props.match.params.id);
    }

    onChangeName(e) {
        const name = e.target.value;

        this.setState(function (prevState) {
            return {
                currentMedication: {
                    ...prevState.currentMedication,
                    name: name
                }
            };
        });
    }

    onChangeSideEffects(e) {
        const sideEffects = e.target.value;

        this.setState(prevState => ({
            currentMedication: {
                ...prevState.currentMedication,
                sideEffects: sideEffects
            }
        }));
    }

    onChangeDosage(e) {
        const dosage = e.target.value;

        this.setState(prevState => ({
            currentMedication: {
                ...prevState.currentMedication,
                dosage: dosage
            }
        }));
    }

    onChangeDosageUnits(e) {
        const dosageUnits = e.target.value;

        this.setState(prevState => ({
            currentMedication: {
                ...prevState.currentMedication,
                dosageUnits: dosageUnits
            }
        }));
    }


    getMedication(id) {
        MedicationService.getMedicationById(id)
            .then(response => {
                this.setState({
                    currentMedication: response.data
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

    updateMedication() {
        MedicationService.updateMedication(
            this.state.currentMedication.id,
            this.state.currentMedication
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The medication was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    error: true
                })
            });
    }

    deleteMedication() {
        MedicationService.deleteMedication(this.state.currentMedication.id)
            .then(response => {
                console.log(response.data);
                this.props.history.push('/medications')
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    error: true
                })
            });
    }

    render() {
        const {currentMedication} = this.state;
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
                {currentMedication ? (
                    <div className="edit-form">
                        <h4>Medication</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="name">Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="name"
                                    value={currentMedication.name}
                                    onChange={this.onChangeName}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="sideEffects">Side Effects</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="sideEffects"
                                    value={currentMedication.sideEffects}
                                    onChange={this.onChangeSideEffects}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="dosage">Dosage</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="dosage"
                                    value={currentMedication.dosage}
                                    onChange={this.onChangeDosage}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="dosageUnits">Dosage Units</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="dosageUnits"
                                    value={currentMedication.dosageUnits}
                                    onChange={this.onChangeDosageUnits}
                                />
                            </div>
                        </form>

                        <button
                            className="badge badge-danger mr-2"
                            onClick={this.deleteMedication}
                        >
                            Delete
                        </button>

                        <button
                            type="submit"
                            className="badge badge-success"
                            onClick={this.updateMedication}
                        >
                            Update
                        </button>

                        <Link to={'/medications'}>
                            <button className="badge badge-primary mr-2">
                                Back
                            </button>
                        </Link>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br/>
                        <p>Please click on a Medication...</p>
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

export default connect(mapStateToProps)(Medication);
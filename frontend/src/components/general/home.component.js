import React, {Component} from "react";
import {connect} from "react-redux";
import {Redirect} from "react-router-dom";

class Home extends Component {

    render() {
        const {user: currentUser} = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }

        if (currentUser.roles.includes("DOCTOR")) {
            return (
                <div className="container">
                    <header className="jumbotron">
                        <p>
                            Welcome, doctor {currentUser.lastName}! Your patients await you.
                        </p>
                    </header>
                </div>
            );
        }
        if (currentUser.roles.includes("CAREGIVER")) {
            return (
                <div className="container">
                    <header className="jumbotron">
                        <p>
                            Welcome, {currentUser.firstName}! Thank you for your care.
                        </p>
                    </header>
                </div>
            );
        }
        return (
            <div className="container">
                <header className="jumbotron">
                    <p>
                        Welcome, {currentUser.firstName}! Don't forget your medication!
                    </p>
                </header>
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

export default connect(mapStateToProps)(Home);
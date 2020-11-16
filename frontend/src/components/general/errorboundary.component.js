import React from "react";

export default class Error extends React.Component {
    constructor(props) {
        super(props);
        this.state = {hasError: false};
    }

    componentDidCatch(error, info) {
        this.setState({hasError: true});
    }

    render() {
        return <h1>Something went wrong.</h1>;
    }
}
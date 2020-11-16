import axios from "axios";

//const API_URL = "http://localhost:8080/auth/";
const API_URL = 'https://apothecary-backend.herokuapp.com/auth/'

class AuthService {
    login(username, password) {
        return axios
            .post(API_URL + "login", {username, password})
            .then((response) => {
                if (response.data.accessToken) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }
}

export default new AuthService();

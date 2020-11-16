import axios from 'axios';
import authHeader from './auth-header';

//const API_URL = 'http://localhost:8080/information';
const API_URL = 'https://apothecary-backend.herokuapp.com/information'

class InfoService {
    getUserInformation() {
        return axios.get(API_URL, {headers: authHeader()});
    }
}

export default new InfoService();
import axios from 'axios';
import authHeader from './auth-header';

//const API_URL = 'http://localhost:8080/addresses';
const API_URL = 'https://apothecary-backend.herokuapp.com/addresses'


class AddressService {

    getAllAddresses() {
        return axios.get(API_URL, {headers: authHeader()});
    }
}

export default new AddressService();

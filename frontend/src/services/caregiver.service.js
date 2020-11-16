import axios from 'axios';
import authHeader from './auth-header';

//const API_URL = 'http://localhost:8080/caregivers';
const API_URL = 'https://apothecary-backend.herokuapp.com/caregivers'

class CaregiverService {
    getAllCaregivers() {
        return axios.get(API_URL, {headers: authHeader()});
    }

    getCaregiverById(id) {
        return axios.get(API_URL + `/${id}`, {headers: authHeader()});
    }

    createCaregiver(caregiver) {
        return axios.post(API_URL, caregiver, {headers: authHeader()})
    }

    updateCaregiver(id, caregiver) {
        return axios.put(API_URL + `/${id}`, caregiver, {headers: authHeader()})
    }

    deleteCaregiver(id) {
        return axios.delete(API_URL + `/${id}`, {headers: authHeader()})
    }
}

export default new CaregiverService();

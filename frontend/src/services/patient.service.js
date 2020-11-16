import axios from 'axios';
import authHeader from './auth-header';

// const API_URL = 'http://localhost:8080/patients';
const API_URL = 'https://apothecary-backend.herokuapp.com/patients'

class PatientService {
    getAllPatients() {
        return axios.get(API_URL, {headers: authHeader()});
    }

    getPatientById(id) {
        return axios.get(API_URL + `/${id}`, {headers: authHeader()});
    }

    createPatient(caregiver) {
        return axios.post(API_URL, caregiver, {headers: authHeader()})
    }

    updatePatient(id, caregiver) {
        return axios.put(API_URL + `/${id}`, caregiver, {headers: authHeader()})
    }

    deletePatient(id) {
        return axios.delete(API_URL + `/${id}`, {headers: authHeader()})
    }
}

export default new PatientService();

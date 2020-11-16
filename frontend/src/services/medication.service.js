import axios from 'axios';
import authHeader from './auth-header';

//const API_URL = 'http://localhost:8080/medications';
const API_URL = 'https://apothecary-backend.herokuapp.com/medications'

class MedicationService {
    getAllMedications() {
        return axios.get(API_URL, {headers: authHeader()});
    }

    getMedicationById(id) {
        return axios.get(API_URL + `/${id}`, {headers: authHeader()});
    }

    createMedication(medication) {
        return axios.post(API_URL, medication, {headers: authHeader()})
    }

    updateMedication(id, medication) {
        return axios.put(API_URL + `/${id}`, medication, {headers: authHeader()})
    }

    deleteMedication(id) {
        return axios.delete(API_URL + `/${id}`, {headers: authHeader()})
    }

    getByName(name) {
        return axios.get(API_URL + `?name=${name}`, {headers: authHeader()});
    }
}

export default new MedicationService();
import axios from 'axios';
import authHeader from './auth-header';

//const API_URL = 'http://localhost:8080/doctors';
const API_URL = 'https://apothecary-backend.herokuapp.com/doctors'

class DoctorService {
    getAllDoctors() {
        return axios.get(API_URL, {headers: authHeader()});
    }

    getDoctorById(id) {
        return axios.get(API_URL + `/${id}`, {headers: authHeader()});
    }

    createDoctor(caregiver) {
        return axios.post(API_URL, caregiver, {headers: authHeader()})
    }

    updateDoctor(id, caregiver) {
        return axios.put(API_URL + `/${id}`, caregiver, {headers: authHeader()})
    }

    deleteDoctor(id) {
        return axios.delete(API_URL + `/${id}`, {headers: authHeader()})
    }
}

export default new DoctorService();

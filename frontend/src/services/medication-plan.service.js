import axios from 'axios';
import authHeader from './auth-header';

// const API_URL = 'http://localhost:8080/patients';
// const API_URL_PATIENT = 'http://localhost:8080/medication-plans';
const API_URL = 'https://apothecary-backend.herokuapp.com/patients'
const API_URL_PATIENT = 'https://apothecary-backend.herokuapp.com/medication-plans'

class MedicationPlanService {
    getAllMedicationPlans(patientId) {
        return axios.get(API_URL + `/${patientId}/medication-plans`, {headers: authHeader()});
    }

    createMedicationPlan(patientId, medicationDetails) {
        return axios.post(API_URL + `/${patientId}/medication-plans`, medicationDetails, {headers: authHeader()})
    }

    deleteMedicationPlan(id) {
        return axios.delete(API_URL + `/${id}`, {headers: authHeader()})
    }

    getAllMedicationPlansForLoggedPatient() {
        return axios.get(API_URL_PATIENT, {headers: authHeader()});
    }
}

export default new MedicationPlanService();

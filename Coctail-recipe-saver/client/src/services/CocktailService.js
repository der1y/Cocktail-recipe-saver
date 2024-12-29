import axios from 'axios';

export default {
    get() {
        return axios.get('/cocktails');
    },
    getCocktailById(id) {
        return axios.get(`/cocktails/${id}`);
    },
    createCocktail(cocktail) {
        return axios.post(`/cocktails/`, cocktail)
    },
    updateCocktail(id, cocktail) {
        return axios.put(`/cocktails/${id}`, cocktail)
    },
    deleteCocktail(id) {
        return axios.delete(`/cocktails/${id}`);
    },
    getPublicUsersCocktails(id) {
        return axios.get(`/users/${id}/cocktails`);
    }

}
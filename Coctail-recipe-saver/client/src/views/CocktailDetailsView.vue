<template>
    <CocktailDetails :cocktail="cocktail"/>
</template>

<script>

import CocktailDetails from '../components/CocktailDetails.vue';
import CocktailService from '../services/CocktailService';

export default {
    components: {
        CocktailDetails
    },
    data() {
        return {
            cocktail: {}
        };
    },
    methods: {
        getCocktail(id) {
            CocktailService.getCocktailById(id).then(response => {
                this.cocktail = response.data;
            })
            .catch(error => {
                this.handleErrorResponse(error, "accessing");
            });
        },
        handleErrorResponse(error, verb) {
            if (error.response) {
                if (error.response.status == 404) {
                    this.$router.push({ name: 'NotFoundView' });
                } else if (error.response.status == 403) {
                    this.$router.push({ name: 'NoAccessView'})
                } else {
                    this.$store.commit('SET_NOTIFICATION',
                        `Error ${verb} cocktail. Response received was "${error.response.statusText}".`);
                }
            } else if (error.request) {
                this.$store.commit('SET_NOTIFICATION', `Error ${verb} cocktail. Server could not be reached.`);
            } else {
                this.$store.commit('SET_NOTIFICATION', `Error ${verb} cocktail. Request could not be created.`);
            }
        }
    },
    created() {
        // Get the cocktailId from the route to pass into the method.
        this.getCocktail(this.$route.params.cocktailId);
    },
    
}
</script>

<style></style>
<template>
    <div class="container">
        <h1>Edit Cocktail</h1>
        <cocktail-form :cocktail="cocktail" />
    </div>
</template>
  
  <script>
  
  import CocktailService from '../services/CocktailService';
  import CocktailForm from '../components/CocktailForm.vue';
  
  export default {
    components: {
      CocktailForm
    },
    data() {
      return {
        cocktail: {},
      }
    },
    methods: {
      getCocktail(id) {
        CocktailService.getCocktailById(id)
          .then(response => {
            this.cocktail = response.data;
          })
          .catch(error => {
            this.handleErrorResponse(error, "getting")
          })
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
    } 
  };
  </script>
  
<style scoped>
.container {
     display: flex;
     flex-direction: column;
     justify-content: center;
}
h1{
    text-align: center;
}
</style>
  
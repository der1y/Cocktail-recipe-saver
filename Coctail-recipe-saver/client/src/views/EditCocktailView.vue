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
            if (error.response) {
              if (error.response.status == 404) {
                this.$router.push({name: 'NotFoundView'});
              } else {
                this.$store.commit('SET_NOTIFICATION',
                `Error getting cocktail. Response received was "${error.response.statusText}".`);
              }
            } else if (error.request) {
              this.$store.commit('SET_NOTIFICATION', `Error getting cocktail. Server could not be reached.`);
            } else {
              this.$store.commit('SET_NOTIFICATION', `Error getting cocktail. Request could not be created.`);
            }
          })
      }
    },
    created() {
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
  
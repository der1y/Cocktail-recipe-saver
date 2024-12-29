<template>
    <div class="container">
        <h1>Add New Cocktail</h1>
        <cocktail-form :cocktail="cocktail" />
    </div>
</template>

<script>
import CocktailForm from '../components/CocktailForm.vue';

export default {
    components: {
        CocktailForm
    },
    data() {
        return {
            cocktail: {
                id: 0,
                name: '',
                ingredients: [
                    {
                        name: '',
                        measurement: 0.0,
                        unit: '',
                    }
                ],
                glass: {
                    name: ''
                },
                instructions: '',
                public: ''
            }
        }
    }
}
</script>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

h1 {
    text-align: center;
}
</style>
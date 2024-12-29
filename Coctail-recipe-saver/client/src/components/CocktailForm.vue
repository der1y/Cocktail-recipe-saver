<template>
    <div id="newCocktail">
        <hr />
        <form @submit.prevent="submitCocktail">
            <div class="field">
                <input type="textarea" id="name" name="name" v-model="editCocktail.name" placeholder="Name" />
            </div>
            <div class="field">
                <div v-for="(ingredient, index) in editCocktail.ingredients" :key="index">
                    <input v-model="ingredient.name" type="text" placeholder="Ingredient" />
                    <input v-model="ingredient.measurement" type="number" step="0.01" placeholder="0"
                        id="measurement" />
                    <select name="unit" id="unit" v-model="ingredient.unit">
                        <option disabled value="">Unit</option>
                        <option value="oz">oz</option>
                        <option value="dash">dash(s)</option>
                        <option value="drop">drop(s)</option>
                        <option value="bsp">bsp</option>
                    </select>

                    <button @click.prevent="removeInputField(index)">X</button>
                </div>
                <button @click.prevent="addNewInputField">Add new ingredient</button>
            </div>
            <div class="field">
                <input type="text" id="glass" name="glass" v-model="editCocktail.glass.name" placeholder="Glass" />
            </div>
            <div class="field">
                <input type="text" id="instructions" name="instructions" v-model="editCocktail.instructions"
                    placeholder="Instructions" />
            </div>
            <div class="field">
                <select name="public" id="public" v-model="editCocktail.public">
                    <option disabled value="">Is it public?</option>
                    <option :value="true">Is it public: True</option>
                    <option :value="false">Is it public: False</option>
                </select>
            </div>
            <div class="actions">
                <button class="submit" type="submit">Submit</button>
                <button class="cancel" type="button" @click="cancelForm">Cancel</button>
            </div>
        </form>
    </div>
</template>

<script>
import CocktailService from '../services/CocktailService';

export default {
    props: {
        cocktail: {
            type: Object,
            required: true
        },
    },
    data() {
        return {
            // Copy the prop details into the editCocktail object.
            editCocktail: {
                id: this.cocktail.id,
                name: this.cocktail.name,
                ingredients: this.cocktail.ingredients,
                instructions: this.cocktail.instructions,
                glass: this.cocktail.glass,
                public: this.cocktail.public,
                userId: this.cocktail.userId
            },

        };
    },
    methods: {
        // Create a new field for a new ingredient.
        addNewInputField() {
            this.editCocktail.ingredients.push({
                name: '',
                measurement: 0.0,
                unit: '',
            });
        },

        // Remove any of the ingredient fields.
        removeInputField(index) {
            this.editCocktail.ingredients.splice(index, 1);
        },
        submitCocktail() {
            // Check for add or edit
            if (this.editCocktail.id === 0) {
                CocktailService
                    .createCocktail(this.editCocktail)
                    .then(response => {
                        if (response.status === 201) {
                            this.$router.push({ name: 'home' });
                        }
                    })
                    .catch(error => {
                        this.handleErrorResponse(error, "creating");
                    });
            } else {
                CocktailService
                    .updateCocktail(this.editCocktail.id, this.editCocktail)
                    .then(response => {
                        if (response.status === 200) {
                            this.$router.push({ name: 'home' });
                        }
                    })
                    .catch(error => {
                        this.handleErrorResponse(error, "updating");
                    });
            }
        },
        cancelForm() {
            this.$router.back();
        },
        handleErrorResponse(error, verb) {
            if (error.response) {
                if (error.response.status == 404) {
                    this.$router.push({ name: 'NotFoundView' });
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
    }
}
</script>

<style scoped>
#measurement {
    width: 50px;
}

.field {
    display: flex;
    flex-direction: column;
    margin: 0px;
}

#measurement {
    width: 60px;
}

#newCocktail {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

input, button, select {
    border: 1px solid #283618;
    margin: 0px;
}

button:hover {
    cursor: pointer;
}

</style>
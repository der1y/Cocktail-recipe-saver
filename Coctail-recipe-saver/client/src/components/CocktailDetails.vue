<template>
    <span>
        <header id="thing">
            <div>
                <h2 id="title"> {{ cocktail.name }}</h2>
            </div>
            <div class="actions">
                <button class="edit" v-if="$store.state.user.id === cocktail.userId || ($store.state.user.authoritiesString === 'ROLE_ADMIN')"
                    @click="$router.push({ name: 'EditCocktail', params: { cocktailId: cocktailId } })">Edit</button>
                <button class="delete" @click="deleteCocktail()" v-if="($store.state.user.id === cocktail.userId) || ($store.state.user.authoritiesString === 'ROLE_ADMIN')">Delete</button>
            </div>
        </header>
        <div id="detail-box">
            <img id="detail-foto" src="../../design/espressoMartini.jpg" />

            <ul id="ingredients">
                <h1>Ingredients</h1>
                <li v-for="ingredient in cocktail.ingredients" :ingredient="ingredient" :key="ingredient.id"
                    class="list-style">
                    {{ ingredient.measurement }} {{ ingredient.unit }} {{ ingredient.name }}
                </li>
            </ul>
            <ul id="method">
                <h1>Method</h1>
                <li class="list-style"> {{ cocktail.instructions }}</li>
            </ul>
        </div>
    </span>
</template>

<script>
import CocktailService from '../services/CocktailService';

export default {
    props: ['cocktail'],
    methods: {
        deleteCocktail() {
            if (confirm("Are you sure you want to delete this cocktail? This action cannot be undone.")) {

                // TODO - Do a delete, then navigate Home on success
                // For errors, call handleErrorResponse
                CocktailService
                    .deleteCocktail(this.cocktail.id)
                    .then(response => {
                        if (response.status === 204) {
                            this.$router.push({ name: 'home' });
                        }
                    });
            }
        },
        handleErrorResponse(error, verb) {
            if (error.response) {
                if (error.response.status == 404) {
                    this.$router.push({ name: 'NotFoundView' });
                } else if (error.response.status == 403) {
                    this.$router.push({ name: 'NotFoundView'})
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
#detail-foto {
    height: 500px;
    border-radius: 10px;
}

#detail-box {
    display: flex;
    flex-direction: row;
    width: 100vw;
    flex-wrap: wrap;
    position: absolute;
    top: 200px;
    padding: 10px 200px;
    justify-content: space-between;
    left: 0;

}

.list-style,
h1 {
    list-style: none;
    padding: 10px 50px 10px 0px;
}

.list-style:not(:last-child),
h1 {
    border-bottom: 1px solid #7A9598;
}

h1,
#method {
    margin: 0;
    font-size: 20px;
}

#ingredients {
    padding: 20px;
}

#method {
    width: 500px;
    padding: 20px;
}

ul {
    border: 4px solid #7A9598;
    border-radius: 10px;
    margin: 0;
}

#thing {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    width: 85vw;
    padding: 0px 20px;
    border-bottom: 1px solid #7A9598;
    top: 100px;
    left: 0;
    position: relative;
}

#title {
    font-size: 25px;
    padding: 20px 0px;
    margin: 0;
    color: #283618;
    flex-grow: 1;
}

.actions {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    flex-shrink: 0;
    gap: 10px;
}

.edit:hover,
.delete:hover {
    cursor: pointer;
}

.edit,
.delete {
    border: 1px solid #283618;
    color: #283618;
}
</style>
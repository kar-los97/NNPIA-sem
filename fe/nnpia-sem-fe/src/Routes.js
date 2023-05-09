import React from 'react';
import {Route,Switch} from "react-router-dom";
import Home from "./pages/Home";
import Restaurants from "./pages/Restaurant/Restaurants";
import RestaurantForm from "./pages/Restaurant/RestaurantForm";
import RestaurantEvaluation from "./pages/Restaurant/RestaurantEvaluation";
import {EvaluationForm} from "./pages/Evaluation/EvaluationForm";

const Routes = () =>{
    return(
        <Switch>
            <Route exact path={"/"} component={Home}/>
            <Route exact path={"/restaurant/my/"} component={Restaurants}/>
            <Route exact path={"/restaurant/my/add"} component={RestaurantForm}/>
            <Route exact path={"/restaurant/my/detail/:id"} component={RestaurantForm}/>
            <Route exact path={"/restaurant/evaluation/add/:id"} component={EvaluationForm}/>
            <Route exact path={"/restaurant/evaluation/:id"} component={RestaurantEvaluation}/>
            <Route exact path={"/restaurant"} component={Restaurants}/>

        </Switch>
    )
}

export default Routes;
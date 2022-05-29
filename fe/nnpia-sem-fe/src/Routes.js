import React from 'react';
import {Route,Switch} from "react-router-dom";
import LoginForm from "./pages/User/LoginForm";
import Home from "./pages/Home";
import Restaurants from "./pages/Restaurant/Restaurants";
import RestaurantForm from "./pages/Restaurant/RestaurantForm";

const Routes = () =>{
    return(
        <Switch>
            <Route exact path={"/"} component={Home}/>
            <Route exact path={"/restaurant/my/"} component={Restaurants}/>
            <Route exact path={"/restaurant/my/add"} component={RestaurantForm}/>
            <Route exact path={"/restaurant/my/detail/:id"} component={RestaurantForm}/>
            <Route exact path={"/restaurant"} component={Restaurants}/>
        </Switch>
    )
}

export default Routes;
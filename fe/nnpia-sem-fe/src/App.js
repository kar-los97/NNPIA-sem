import React from "react";
import './App.css';
import {useEffect, useState} from "react";
import LoginForm from "./pages/User/LoginForm";
import Layout from "./components/Layout/Layout";
import Routes from "./Routes";
import LoginLayout from "./components/LoginForm/LoginLayout";
import {useHistory} from "react-router-dom";

const App = () => {

    let [loading,setLoading] = useState(true);
    let [loggedInUser,setLoggedInUser] = useState(null);
    let history = useHistory();


    useEffect(()=>{
        authorize();
    },[]);

    //Autorizace při každém načtení jakékoliv stránky
    const authorize = () =>{
        let email = localStorage.getItem('email');
        let role = localStorage.getItem('role');
        if(!role && role==="NON_REGISTER"){
            setLoggedInUser({"email":email,"role":role});
            setLoading(false);
        }
        if(!role || !email){
            setLoading(false);
            history.push("/login");
        }else{
            setLoggedInUser({"email":email,"role":role});
            setLoading(false);
        }
    }

    if(loading) return <>LOADING MAIN PAGE</>;
    if(!loggedInUser) return <LoginLayout><LoginForm setLoggedInUser={setLoggedInUser}/></LoginLayout>
    if(loggedInUser) return <Layout loggedInUser={loggedInUser}><Routes/></Layout>
}

export default App;

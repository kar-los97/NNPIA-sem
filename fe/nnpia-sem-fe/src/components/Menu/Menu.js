import React, {useState} from "react";
import {Link} from "react-router-dom";
import {axios} from "../../axiosConfig";

const Menu = () => {
    const role = localStorage.getItem("role");
    let [active,setActive] = useState(1);
    const logout = () =>{
        localStorage.removeItem('role');
        localStorage.removeItem('email');
        axios.defaults.headers.common['Authorization'] = undefined;
        window.location.reload();

    }
    const getStyle = (id)=>{
        if(id===active){
            return "flex-sm-fill m-1 text-sm-center nav-link bg-dark shadow-lg";
        }else{
            return "flex-sm-fill m-1 text-sm-center nav-link bg-info";
        }
    }
    return (
        <div>
            <nav className={"nav nav-pills flex-column flex-sm-row bg-light"}>
                <div className={getStyle(1)}><Link onClick={()=>setActive(1)} className={"text-white text-uppercase text-decoration-none"} to={"/"}>Domů</Link></div>
                {role==="Admin"?
                    <div className={getStyle(2)}><Link onClick={()=>setActive(2)} className={"text-white text-uppercase text-decoration-none"} to={"/restaurant/my"}>Moje Restaurace</Link></div>
                    :<></>
                }
                <div className={getStyle(3)}><Link onClick={()=>setActive(3)} className={"text-white text-uppercase text-decoration-none"} to={"/restaurant"}>Restaurace</Link></div>
                <div className={getStyle(4)}><Link onClick={()=>setActive(4)} className={"text-white text-uppercase text-decoration-none"} to={"/evaluation"}>Má hodnocení</Link></div>
                <div className={getStyle(5)}><Link onClick={logout} className={"text-white text-uppercase text-decoration-none"}>Odhlásit se</Link></div>
            </nav>
        </div>
    )
}

export default Menu;
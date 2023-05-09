import React from "react";
import Menu from "../Menu/Menu";
import Footer from "../Footer/Footer";

const Layout = ({children, loggedInUser}) => {

    return (
        <div className={""}>
            <Menu loggedInUser={loggedInUser}/>
                <div className={"container"}>
                    {children}
                </div>
            <Footer/>
        </div>
    )
}

export default Layout;
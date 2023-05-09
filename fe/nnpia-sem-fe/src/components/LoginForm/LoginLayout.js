import React from "react";

const LoginLayout = ({children}) => {
    return (
        <div className="d-flex justify-content-center align-items-center bg-light" style={{height: "100vh"}}>
            <div className={"card w-50 text-center align-content-center shadow-lg"}>
                <div className={"card-header bg-info justify-content-center text-center"}>
                    <h1 className={"card-title display-4"}>Hodnocení restaurací</h1>
                    <h2 className={"card-subtitle display-5"}>Přihlášení</h2>

                </div>
                <div className={"card-body bg-light"}>
                    {children}
                </div>

            </div>
        </div>
    );
}

export default LoginLayout;
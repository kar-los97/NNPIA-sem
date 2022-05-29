import React from "react";

const LoginLayout = ({children}) => {
    return (
        <div className={"d-flex align-items-center justify-content-center"}>
            <div className={"card w-75 text-center align-content-center"}>
                <div className={"card-header bg-info justify-content-center text-center"}>
                    <div className={"card-title"}>Hodnocení restaurací</div>
                    <div className={"card-subtitle"}>Přihlášení</div>

                </div>
                <div className={"card-body bg-light"}>
                    {children}
                </div>

            </div>
        </div>
    );
}

export default LoginLayout;
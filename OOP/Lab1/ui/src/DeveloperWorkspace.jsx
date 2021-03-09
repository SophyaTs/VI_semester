import { Component } from "react";
import Greeting from "./Greeting.jsx";

class DeveloperWorkspace extends Component{
    render(){
        return(
            <div>
                <Greeting />
                <br/>
                Developer
            </div>
        )
    }
}

export default DeveloperWorkspace;
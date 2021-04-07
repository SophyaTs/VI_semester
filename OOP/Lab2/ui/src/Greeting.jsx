import { Component } from "react";
import $,{ajax, post} from "jquery";
import Keycloak from 'keycloak-js';
//import Cookies from 'universal-cookie';

//const cookies = new Cookies();

class Greeting extends Component{
    constructor(props){
        super(props);
        /* this.state = { 
            keycloak: null, 
            authenticated: false,
            exists: false, 
          }; */
    }

    componentDidMount(){
        $("#username").text(localStorage.getItem("employeeName"));

        $(document).on("click", "#exitbtn", function(event){
            this.props.keycloak.logout();
        }.bind(this));
    }

    render(){
        return(
            <div class = 'greetingtext'>
                Welcome, <label id = "username"></label>!
                <div class='exitbutton' id = "exitbtn"/>
            </div>
        )
    }
}

export default Greeting;
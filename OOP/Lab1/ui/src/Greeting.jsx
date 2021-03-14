import { Component } from "react";
import $,{ajax} from "jquery";
import Cookies from 'universal-cookie';

const cookies = new Cookies();

class Greeting extends Component{
    constructor(props){
        super(props);
    }

    componentDidMount(){
        $("#username").text(localStorage.getItem("username"));

        $(document).on("click", "#exitbtn", function(event){
            //remove old credentials
            cookies.remove("LOGIN");
            cookies.remove("PASSWORD");
            localStorage.setItem("username",'');
            localStorage.setItem("emloyee_id",0);
            
            $.ajax({
                type:"PUT",
                url: 'http://localhost:8080/Lab1/index',
                crossDomain: true,
                xhrFields: { withCredentials: true },
            })
            window.location.href='/'; 
        }.bind(this));
    }

    render(){
        return(
            <div>
                Welcome, <label id = "username"></label>!
                <button id = "exitbtn">Exit</button>
            </div>
        )
    }
}

export default Greeting;
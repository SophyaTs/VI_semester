import { Component } from "react";
import $,{ajax, post} from "jquery";
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
            cookies.remove("LOGIN",{path:'/Lab1'});
            cookies.remove("PASSWORD",{path:'/Lab1'});
            cookies.remove("ROLE",{path:'/Lab1'});
            localStorage.setItem("username",'');
            localStorage.setItem("emloyee_id",0);
            
            $.ajax({
                type: "PUT",
                url: 'http://localhost:8080/Lab1/index',
                contentType: "application/json",
                crossDomain: true,
                xhrFields: { withCredentials: true },
                success: function(){
                    window.location.href='/'; 
                }
            });           
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
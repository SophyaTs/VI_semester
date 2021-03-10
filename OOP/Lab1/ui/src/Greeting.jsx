import { Component } from "react";
import $ from "jquery";

class Greeting extends Component{
    constructor(props){
        super(props);
    }

    componentDidMount(){
        $("#username").text(localStorage.getItem("username"));

        $(document).on("click", "#exitbtn", function(event){
            localStorage.setItem("username",'');
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
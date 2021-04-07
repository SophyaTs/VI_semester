import './style.css';
import { Component } from "react";

class Header extends Component{
    render(){
        return(
            <div class = 'header'>
                <div class = 'headertextbox'>
                    <p class = 'headertext'>Development Team</p>
                </div>
            </div>
        )
    }
}

export default Header;
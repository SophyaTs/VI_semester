import { Component } from "react";
import Greeting from "./Greeting.jsx";
import $, { ajax, get} from "jquery";
import Keycloak from 'keycloak-js';

class DeveloperWorkspace extends Component{
    constructor(props){
        super(props);
        this.state = {
            keycloak: null, 
            authenticated: false,
            task_id: 0,
            hrs: 0,
            show_input: false
        };
        this.componentDidMount = this.componentDidMount.bind(this);
        this.onHandleChange = this.onHandleChange.bind(this);
        //this.getTasks = this.getTasks.bind(this);
        this.render = this.render.bind(this);
    }

    componentDidMount(){
        localStorage.setItem("taskId",0);
        $.get({
            url: 'http://localhost:8080/dev/'+localStorage.getItem("employeeId"),
                success: function(responseJSON){
                    let select=$('#tasksD');
                    $.each(responseJSON, function(index, item) { // Iterate over the JSON array
                        select.append('<option name="tasksD" value="'+item.id+'">'+item.name+'</option>');                      
                    }.bind(this));
                }.bind(this),
                error: function(){
                    window.location.href = '/';
            },
        });

        $(document).on("change", "#tasksD", function(event){
            localStorage.setItem("taskId", $('#tasksD').find(":selected").val()) ;           
            this.setState({
                show_input: true,
                //task_id: new_task
            })
            $.get({
                url:'http://localhost:8080/dev/'+localStorage.getItem("employeeId")+'/task/'+localStorage.getItem("taskId"),
                success: function(response){
                    var hours = parseInt(response)
                    this.setState({
                        hrs: hours
                    })
                }.bind(this),
            });
            $("#hours").css('visibility', 'visible');
        }.bind(this));

        $(document).on("click", "#savebtnD", function(event){
            $.ajax({
                type:'PUT',
                url: 'http://localhost:8080/dev/'+localStorage.getItem("employeeId")+'/task/'+localStorage.getItem("taskId")+'/update',
                contentType: "application/json",
                data: this.state.hrs, 
                success: function(){}                 
            });
        }.bind(this));
    }

    onHandleChange(e){
        this.setState({
          hrs: e.target.value
        });
    }
    
    render(){ 
                return(
                    <div>
                        <Greeting keycloak={this.props.keycloak}/>
                        <table class = "grid">
                            <tr>
                                <td class = "gridline labelcol">Task:</td>
                                <td class = "gridline inputcol">
                                <select name = "tasksD" id = "tasksD">
                                    { !this.state.show_input ? <option name="tasksD" value='0' id="none">Choose task</option> : null}
                                </select>
                                </td>
                            </tr>
                            { this.state.show_input ? <tr>                       
                                <td class = "gridline labelcol">Spent hours:</td>
                                <td class = "gridline inputcol">
                                    <input 
                                        type = "number" 
                                        id = "hours" 
                                        value = {this.state.hrs} 
                                        min = '0' 
                                        step = '1'  
                                        onChange={this.onHandleChange}
                                    />
                                </td>
                            </tr> : null}
                            <tr>
                                <td colSpan='2'>
                                    <center><button id = "savebtnD">Save</button></center>
                                </td> 
                            </tr>
                        </table>
                        
                    </div>
                )

    }
}

export default DeveloperWorkspace;
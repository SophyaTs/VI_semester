import { Component } from "react";
import Greeting from "./Greeting.jsx";
import $, { ajax, post } from "jquery";

class DeveloperWorkspace extends Component{
    constructor(props){
        super(props);
        this.state = {
            task_id: 0,
            hrs: 0,
            show_input: false
        };
        this.componentDidMount = this.componentDidMount.bind(this);
        this.onHandleChange = this.onHandleChange.bind(this);
        //this.getTasks = this.getTasks.bind(this);
        this.render = this.render.bind(this);
    }
    
    renderOption(index, item){
        return(<option name="tasks" value={item.id}>{item.name}</option>); 
    }

    componentDidMount(){
        $.post({
            url: 'http://localhost:8080/Lab1/dev',
            contentType: "application/json",
            data: JSON.stringify({
                doGet: true,
                employee_id: localStorage.getItem("employee_id")
            }),
            success: function(responseJSON){
                let select=$('#tasks');
                $.each(responseJSON, function(index, item) { // Iterate over the JSON array
                    /* var list = this.state.tasks;
                    list.push(item);
                    this.setState({
                        tasks: list
                    }); */

                    select.append('<option value="'+item.id+'">'+item.name+'</option>');
                    //$("#tasks").append(<option name="tasks" value={item.id}>{item.name}</option>); 
                }.bind(this));
            }.bind(this)
        });

        $(document).on("change", "#tasks", function(event){
            this.setState({
                show_input: true
            });
            var new_task = $('#tasks').find(":selected").val();
            this.setState({
                task_id: new_task
            })
            $.post({
                url:'http://localhost:8080/Lab1/dev',
                contentType: "application/json",
                data: JSON.stringify({
                    employee_id: localStorage.getItem("employee_id"),
                    task_id: this.state.task_id
                }),
                success: function(response){
                    var hours = parseInt(response)
                    this.setState({
                        hrs: hours
                    })
                }.bind(this)
            });
            $("#hours").css('visibility', 'visible');
        }.bind(this));

        $(document).on("click", "#savebtn", function(event){
            $.ajax({
                type: "PUT",
                url: 'http://localhost:8080/Lab1/dev',
                contentType: "application/json",
                data: JSON.stringify({
                    employee_id: localStorage.getItem("employee_id"),
                    task_id: this.state.task_id,
                    hrs: this.state.hrs 
                })               
            });
        }.bind(this));
    }

    onHandleChange(e){
        this.setState({
          hrs: e.target.value
        });
      }
    
    render(){
 /*        const items = [];
        for (const [index, value] of this.state.tasks.entries()) {
            items.push(this.renderOption(index, value));
        }  */  
        return(
            <div>
                <Greeting />
                <br/>
                <select name = "tasks" id = "tasks">
                    { !this.state.show_input ? <option name="tasks" value='0' id="none">Choose task</option> : null}
                </select><br/><br/>
                { this.state.show_input ? 
                    <div>
                        <input 
                            type = "number" 
                            id = "hours" 
                            value = {this.state.hrs} 
                            min = '0' 
                            step = '1'  
                            onChange={this.onHandleChange}
                        /><br/><br/>
                        <button id = "savebtn">Save</button>
                    </div> 
                : null}
            </div>
        )
    }
}

export default DeveloperWorkspace;
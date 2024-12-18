/*
 * Copyright (C) 2022 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';


interface EdgeListProps {
    onChange(edges: string[][]): void;
}
interface EdgeListState {
    textValue: string;
}
/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            textValue: "Input an edge with this format: x1 y1 x2 y2 COLOR",
        };
    }
    textChange(event: any) {
        this.setState({
            textValue: event.target.value,
        });
    }


    //Pressing buttonDraw will call this function
    //The purpose of this function is to split the user input which should be separated by spaces into a matrix
    //Every row in the matrix has one edge while each column is considered a property of a corresponding edge
    //This method also calls checkInput in order to validate and check that the client is properly type correctly
    buttonDraw() {
        console.log('Draw onClick was called');
        let res: string[][] = [];
        this.props.onChange(res);

        //This parses and splits the input
        this.state.textValue.split(/\r?\n/).forEach(element => {
            res.push(element.split(" "));
        })

        //This validates the input
        if(this.checkInput(res)) {
            this.props.onChange(res);
        }
    }

    //This method validates user input to check if they have the correct number of arguments for the edges
    //and is used to check if the input is within the bounds of the map


    checkInput(result: string[][]){
        for(let i = 0; i < result.length; i++){
            if(result[i].length !== 5) {
                alert("Illegal arguments");
                this.setState({textValue: "Your input is invalid!"});
                return false;


            }


            if(isNaN(parseFloat(result[i][0])) || parseFloat(result[i][0]) < 0 || parseFloat(result[i][0]) > 4000) {
                alert("x1 is out of bounds");
                this.setState({textValue: "Your input is invalid!"});
                return false;

            }


            if(isNaN(parseFloat(result[i][1])) || parseFloat(result[i][1]) < 0 || parseFloat(result[i][1]) > 4000)  {
                alert("y1 is out of bounds");
                this.setState({textValue: "Your input is invalid!"});
                return false;

            }


            if(isNaN(parseFloat(result[i][2])) || parseFloat(result[i][2]) < 0 || parseFloat(result[i][2]) > 4000)  {
                alert("x2 is out of bounds");
                this.setState({textValue: "Your input is invalid!"});
                return false;

            }


            if(isNaN(parseFloat(result[i][3])) || parseFloat(result[i][3]) < 0 || parseFloat(result[i][3]) > 4000) {
                alert("y2 is out of bounds");
                this.setState({textValue: "Your input is invalid!"});
                return false;

            }


            if(!isNaN(parseFloat(result[i][4]))) {
                alert("COLOR input is invalid");
                this.setState({textValue: "Your input is invalid."});
                return false;

            }
        }
        return true;

    }





    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    onChange={(event) => this.textChange(event)}
                    value={this.state.textValue}
                /> <br/>
                <button onClick={() => {this.buttonDraw();}}>Draw</button>
                <button onClick={() => {this.props.onChange([]);}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;

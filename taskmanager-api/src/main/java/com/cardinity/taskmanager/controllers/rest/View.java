package com.cardinity.taskmanager.controllers.rest;

public interface View {
    interface HttpMethodView {
        //view for project POST call request body
        public static interface POST{

        }
        //view for project PUT call request body
        public static interface PUT{

        }

    }

    interface TaskResponseView{
    }

    interface ProjectResponseView{

    }


}

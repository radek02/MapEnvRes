import { Fragment, useCallback, useMemo, useState } from "react";
import { clusters, reservationsByEnvironmentId_1, environments } from "../sampleData.json";
import { useParams } from "react-router-dom";
import { Container } from "react-bootstrap";
import UserHeader from "../components/UserHeader";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { Calendar, SlotInfo, Views, momentLocalizer } from 'react-big-calendar';
import moment from "moment";
import 'moment-timezone';
import AddReservationForm, { ReservationProps } from "../components/AddReservationForm";

interface Reservation {
    id: number;
    userGroupID: number;
    environmentID: number;
    clusterID: number;
    startTime: string;
    endTime: string;
}

export interface CalendarEvent {
    id: number;
    title: string;
    resourceId: number;
    start: Date;
    end: Date;
}

// START GENAI@CHATGPT4
function transformReservationsToCalendarEvents(reservations: Reservation[]): CalendarEvent[] {
    return reservations.map(reservation => {
        return {
            id: reservation.id,
            title: 'Tu jest bardzo dluga nazwa', // TODO
            resourceId: reservation.clusterID,
            start: new Date(reservation.startTime),
            end: new Date(reservation.endTime)
        };
    });
}
// END GENAI@CHATGPT4

function ReservationsForEnvironment() {
    const eventsArr: CalendarEvent[] = transformReservationsToCalendarEvents(reservationsByEnvironmentId_1);

    const [inputStartTime, setInputStartTime] = useState<string>(new Date().toISOString());
    const [inputEndTime, setInputEndTime] = useState<string>(new Date().toISOString());
    const [selectedClusterId, setSelectedClusterId] = useState<number>(-1);

    const [events, setEvents] = useState(eventsArr);

    moment.tz.setDefault("UTC");
    const localizer = momentLocalizer(moment);

    //const localizer = globalizeLocalizer(Globalize);
    const environmentId = Number(useParams().environmentId);

    const environment = environments.find(environment => environment.id === environmentId);
    const environmentName: string = environment ? environment.name : "";

    //const [data, setData] = useState<Reservation[]>();

    //setData(reservationsByEnvironmentId_1);

    // TODO: maybe useCallback(..., [setEvents])
    const handleSelectSlot =
        (slotInfo: SlotInfo) => {
          setInputStartTime(slotInfo.start.toISOString().slice(0, 16));
          setInputEndTime(slotInfo.end.toISOString().slice(0, 16));
          setSelectedClusterId(slotInfo.resourceId as number);
          
          // setEvents((prev) => [...prev, { 
          //   id: 1, 
          //   title: "myTitle", 
          //   resourceId: slotInfo.resourceId as number, 
          //   start: slotInfo.start, 
          //   end: slotInfo.end
          // }]);
        };
    
    const handleSelectEvent = useCallback(
        (event: CalendarEvent) => {window.alert(event.title)},
        []
      );

    const reservationProps: ReservationProps = {
        inputStartTime,
        setInputStartTime,
        inputEndTime,
        setInputEndTime,
        selectedClusterId,
        setSelectedClusterId,
        events,
        setEvents
      };

    const { defaultDate, scrollToTime, views } = useMemo(
        () => ({
          defaultDate: new Date(2024, 7, 16),
          scrollToTime: new Date(1970, 1, 1, 6),
          views: {
            day: true,
            work_week: true
          }
        }),
        []
      )
    
    return (
        <>
            <UserHeader />
            <div className="d-flex flex-wrap gap-3 align-items-start p-5 w-100">
                <h2 className="text-center w-100">{environmentName}</h2>
                <Calendar
                defaultDate={defaultDate}
                defaultView={Views.DAY}
                events={events}
                localizer={localizer}
                resourceIdAccessor="id"
                resources={clusters}
                resourceTitleAccessor="name"
                step={30}
                views={views}
                onSelectEvent={handleSelectEvent}
                onSelectSlot={handleSelectSlot}
                selectable
                scrollToTime={scrollToTime}
                />
                <div className="bg-light p-4 rounded-4">
                    <AddReservationForm {...reservationProps}/>
                </div>
            </div>
        </>
    );
}

export default ReservationsForEnvironment;
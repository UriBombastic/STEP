// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
 
package com.google.sps;
 
import java.util.*;
 
public final class FindMeetingQuery {
  
  private ArrayList<TimeRange> viableTimeSlots = new ArrayList<TimeRange>(); 
 
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    ArrayList<TimeRange> busyTimes = new ArrayList<TimeRange>();
    ArrayList<TimeRange> optionalBusyTimes = new ArrayList<TimeRange>();
    for(Event e : events) {
      if(eventContains(e.getAttendees(),request.getAttendees())) {
        busyTimes.add(e.getWhen());
      }
      else if(eventContains(e.getAttendees(), request.getOptionalAttendees())) { 
        optionalBusyTimes.add(e.getWhen());
      }
    }
    
    // if(busyTimes.size()==0) // If no one is busy, the whole day is available
    //  viableTimeSlots.add(TimeRange.WHOLE_DAY);
 
    Collections.sort(busyTimes, TimeRange.ORDER_BY_START);
    Collections.sort(optionalBusyTimes, TimeRange.ORDER_BY_START);
 
    int startTime = TimeRange.START_OF_DAY;
    // Find gaps between busy times
    for(TimeRange timeRange : busyTimes) {
      if(timeRange.start() >= startTime) { // Check that this busy time starts after previous one ends
        // Create gap between end of last busy time and this one
        checkInsertTime(startTime, timeRange.start(), (int)request.getDuration(),false);
      }
      startTime = timeRange.end(); //set start of potential meeting time to end of this time slot
    }
    // After iterating, check for timerange at end of day.
    if(startTime < TimeRange.END_OF_DAY) // But only if it hasn't already been reached.
      checkInsertTime(startTime, TimeRange.END_OF_DAY, (int)request.getDuration(), true);
 
    return viableTimeSlots;
  }
 
  private boolean eventContains(Collection<String> eventAttendees, Collection<String> targetAttendees) {
    for(String attendee : targetAttendees) {
        if(eventAttendees.contains(attendee))
          return true;
    }
    return false;
  }
 
  void checkInsertTime(int startTime, int endTime, int duration, boolean doInclusive) {
    TimeRange potentialTime = TimeRange.fromStartEnd(startTime, endTime, doInclusive);
    if(potentialTime.duration() >= duration)
      viableTimeSlots.add(potentialTime);
  }
}

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

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
// This class takes in a collection of events and a meeting request,
// and generates potential times for the meeting. 
public final class FindMeetingQuery { 

  ArrayList<TimeRange> viableTimeSlots = new ArrayList<TimeRange>();
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    int duration = (int)request.getDuration();

    List<TimeRange> requiredBusyTimes = events.stream()
      .filter(e -> eventContains(e.getAttendees(), request, false))
      .map(e -> e.getWhen())
      .collect(Collectors.toList());   
    List<TimeRange> allBusyTimes = events.stream()
      .filter(e -> eventContains(e.getAttendees(), request, true))
      .map(e -> e.getWhen())
      .collect(Collectors.toList()); 

    // Generate available times including optionals
    generatePotentialTimes(allBusyTimes, duration);
 	if (viableTimeSlots.size() == 0) {
      // Generate available times with only requireds.
      generatePotentialTimes(requiredBusyTimes, duration);
    }
    return viableTimeSlots;
  }
 
  private boolean eventContains(Collection<String> eventAttendees, MeetingRequest request, boolean includeOptionals) {
    return (!Collections.disjoint(eventAttendees, request.getAttendees())) ||
      (includeOptionals && !Collections.disjoint(eventAttendees, request.getOptionalAttendees()));
  }
 
  // Takes a list of TimeRanges, iterates over them to find gaps longer than given duration, returns these gaps.
  private ArrayList<TimeRange> generatePotentialTimes(List<TimeRange> busyTimes, int duration) {
    viableTimeSlots = new ArrayList<TimeRange>();
    Collections.sort(busyTimes, TimeRange.ORDER_BY_START); 
    int startTime = TimeRange.START_OF_DAY;

    for (TimeRange timeRange : busyTimes) {
      if (timeRange.start() >= startTime) { 
        // Create gap between end of last busy time and timeRange in question
        insertTimeIfValid(startTime, timeRange.start(), duration,false);   
      }
      // Start time of next possible meeting time would be the end of the current busytime
      startTime = Math.max(startTime, timeRange.end()); 
    }
    // Check for timerange at end of day, but only if it hasn't already been reached.
    if (startTime < TimeRange.END_OF_DAY) { 
      insertTimeIfValid(startTime, TimeRange.END_OF_DAY, duration, true);
    }
    return viableTimeSlots;
  }

  private void insertTimeIfValid(int startTime, int endTime, int duration, boolean doInclusive) {
    TimeRange potentialTime = TimeRange.fromStartEnd(startTime, endTime, doInclusive);
    if (potentialTime.duration() >= duration) {
      viableTimeSlots.add(potentialTime);
    }
  }
}

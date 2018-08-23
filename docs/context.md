# Context

*Group? -> Task -> Work Log*

- Group
  - Name
  - Groups
  - Tasks

- Task
  - Id
  - Title
  - Description
  - Work logs
  
- Work Log
  - Started
  - Duration
  - Comment

# User Stories

- Add task manually
  1. Click new task
  2. Enter Id, Title, Desc

- Add work log manually
  1. Select task -> log work
  2. Started, Comment
  
- Submit work log
  1. Select work log
  2. Submit
  3. Timer stops, sends to server if set up
  3. New work log starts
  
- Add task from repository
  1. Click 'New Task' with URL / Drag URL
  2. Resolve repository from URL
    - If repository doesn't exist
    
      iia. Create new repository
      
      iib. Get authentication if required
      
  3. Resolve Id, Title, Desc
  4. Create task w/ Id, Title, Desc
  5. Start new work log, stop current one
    

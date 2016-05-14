# Resume-Ranking-Service

This is a web application (Resume Ranker) specially useful for recruiters. Many times, the recruiter has to scan through all the resume present in the system manually. This manual process is very time consuming. Also, the 
potential resume could be missed while manually looking at the resumes.<br/>

Using "Resume Ranker", recruiters can save their time drastically. They just have to tell the applicaiton, what keywords they are looking for, and the application will display all the relevant resumes based on keywords. The user 
can access this web app from any workstation and can download the resume to his local workstation as well.<br/>

Apache Lucene is used to filter resumes based on keywords. It uses TF-IDF algorithm to rank the resumes.<br/>

All this resume filtering is happening in real-time. Every resume is fairly considered. There is a java watch service which continuously polls or monitors a directory for any addition of new resume in the system. If this newly 
added resume is relevant, then it will be picked up by the application immediately.<br><br>

Technology Stack: <br/>
1. Java<br/>
2. Apache Lucene<br/>
3. Hibernate<br/>
4. Servlets<br/>
5. Node.Js<br/>
6. AngularJs<br/>
7. HTML/CSS/Bootstrap<br/>

Project Images: <br/><br/>

1. Home page 1<br/>
<img src = "https://github.com/jagrutipatil/Resume-Ranking-Application/blob/master/ProjectScreenshots/HomePage1.PNG"/><br/>

1. Home page 2<br/>
<img src = "https://github.com/jagrutipatil/Resume-Ranking-Application/blob/master/ProjectScreenshots/HomePage2.PNG"/><br/>

1. Home page 3<br/>
<img src = "https://github.com/jagrutipatil/Resume-Ranking-Application/blob/master/ProjectScreenshots/HomePage3.PNG"/><br/>

1. Login page<br/>
<img src = "https://github.com/jagrutipatil/Resume-Ranking-Application/blob/master/ProjectScreenshots/LoginPage.PNG"/><br/>

1. Keywords submission for filtering resumes<br/>
<img src = "https://github.com/jagrutipatil/Resume-Ranking-Application/blob/master/ProjectScreenshots/KeywordsFiltering.PNG"/><br/>

1. Search results on relevant resumes<br/>
<img src = "https://github.com/jagrutipatil/Resume-Ranking-Application/blob/master/ProjectScreenshots/SearchResults.PNG"/><br/>

1. Saved job profile configurations<br/>
<img src = "https://github.com/jagrutipatil/Resume-Ranking-Application/blob/master/ProjectScreenshots/SavedProfileConfigurations.PNG"/><br/>
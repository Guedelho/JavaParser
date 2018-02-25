/*Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.*/

SELECT l.ip FROM log l 
WHERE l.date BETWEEN /*startDate*/ AND /*endDate*/
GROUP BY l.ip 
HAVING COUNT(l.ip) > /*threshold*/;

/*Write MySQL query to find requests made by a given IP.*/

SELECT * FROM log l 
WHERE l.ip = /*ip*/;
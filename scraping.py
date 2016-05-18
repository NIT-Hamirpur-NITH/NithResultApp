from bs4 import BeautifulSoup
import requests
import MySQLdb
conn = MySQLdb.connect("localhost","root","justgoogleit","results" )
cursor = conn.cursor()
arr = [15,11,7,3]
def push_students(rollno,name,cgpi):
	try:
	    cursor.execute('''INSERT into students (roll_no, name,cgpi) values (%s,%s,%s)''',(rollno, name,cgpi))
	    conn.commit()
	except:
	    print conn.rollback()
	    print ("MKK")
def push_semester(rollno,semester,sgpi,cgpi):
	try:
	    cursor.execute('''INSERT into semesters (roll_no,sgpi,cgpi,semester_no) values (%s,%s,%s,%s)''',(rollno,sgpi,cgpi,semester))
	    conn.commit()
	except:
	    print conn.rollback()
def push_subjects(id1,rollno,semester,subname,obtainCR,totalCR):
	try:
	    cursor.execute('''INSERT into subjects (id,roll_no,semester_no,subject_name,obtainCR,TotalCR) values (%s,%s,%s,%s,%s,%s)''',(id1,rollno,semester,subname,obtainCR,totalCR))
	    conn.commit()
	except:
	    print conn.rollback()

def cse_btech():
	roll="1"
	site="1"
	roll_2 = "1"
	for i in range(1):
		roll = roll+str(i+3)
		site = site+str(i+3)
		for j in range(94):
			roll=roll+str(j+701)
			roll_2=roll;
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/scheme"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="1"+str(i+3)

			if len(table)<arr[i+1]:
				continue
			tr=table[arr[i+1]-1].find_all('tr')
			td=tr[1].find_all('td', class_='auto-style5')
			nametr=table[0].find_all('tr')
			nametd=nametr[0].find_all('td', class_='auto-style5')
			print (nametd[1].text).strip()	
			x = (td[2].text)	
			print x[x.find("=")+1:]
			push_students(roll_2,(nametd[1].text).strip(),x[x.find("=")+1:])
			for k in range(arr[i+1]/2):
				tables = page.find_all('table',class_='ewTable')
				trs = tables[k*2+2].find_all('tr')
				tds = trs[1].find_all('td',class_='auto-style5')
				x1 = (tds[0].text)
				y1 = (tds[2].text)
				print x1
				print y1
				push_semester(roll_2,roll_2+str(k+1),x1[x1.find("=")+1:],y1[y1.find("=")+1:])
				trs2 = tables[k*2+1].find_all('tr')
				#print trs2
				subjectnum = len(trs2)
				#print type(subjectnum)
				#print subjectnum
				#print "MKKKKK"
				for l in range(subjectnum-1):
					tds2 = trs2[1+l].find_all('td',class_='auto-style5')
					subname = (tds2[1].text).strip()
					#subptr = int(tds2[5].text)/
					push_subjects(roll_2+str(k+1)+tds2[0].text,roll_2,roll_2+str(k+1),subname,tds2[5].text,str(10*int(tds2[3].text)))
			#print page.prettify()
		roll="1"   


"""def cse_btech():
	roll="IIITU1"
	site="iiituna1"
	roll_2 = "IIITU1"
	for i in range(4):
		roll = roll+str(i+5)
		site = site+str(i+5)
		for j in range(94):
			roll=roll+str(j+201)
			roll_2=roll;
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="IIITU1"+str(i+5)

			if len(table)<arr[i+3]:
				continue
			tr=table[arr[i+3]-1].find_all('tr')
			td=tr[1].find_all('td', class_='ewTableAltRow')
			nametr=table[0].find_all('tr')
			nametd=nametr[0].find_all('td', class_='ewTableAltRow')
			print nametd
			print (nametd[0].text).strip()	
			x = (td[2].text)	
			print x[x.find("=")+1:]
			push_students(roll_2,(nametd[0].text).strip(),x[x.find("=")+1:])
			for k in range(arr[i+3]/2):
				tables = page.find_all('table',class_='ewTable')
				trs = tables[k*2+2].find_all('tr')
				tds = trs[1].find_all('td',class_='ewTableAltRow')
				x1 = (tds[0].text)
				y1 = (tds[2].text)
				print x1
				print y1
				push_semester(roll_2,roll_2+str(k+1),x1[x1.find("=")+1:],y1[y1.find("=")+1:])
				trs2 = tables[k*2+1].find_all('tr')
				#print trs2
				subjectnum = len(trs2)
				#print type(subjectnum)
				#print subjectnum
				#print "MKKKKK"
				for l in range(subjectnum-1):
					tds2 = trs2[1+l].find_all('td',class_='ewTableAltRow')
					subname = (tds2[1].text).strip()
					#subptr = int(tds2[5].text)/
					push_subjects(roll_2+str(k+1)+tds2[0].text,roll_2,roll_2+str(k+1),subname,tds2[5].text,str(10*int(tds2[3].text)))
			#print page.prettify()
		roll="IIITU1"

"""

def civil_btech():
	roll="1"
	site="1"
	for i in range(4):
		roll = roll+str(i+2)
		site = site+str(i+2)
		for j in range(94):
			roll=roll+str(j+101)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/scheme"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="1"+str(i+2)
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='auto-style5')
			
			print td[2].text
			#print page.prettify()
		roll="1"
def eee_btech():
	roll="1"
	site="1"
	for i in range(4):
		roll = roll+str(i+2)
		site = site+str(i+2)
		for j in range(94):
			roll=roll+str(j+201)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/scheme"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="1"+str(i+2)
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='auto-style5')
			

			nametr=table[0].find_all('tr')
			nametd=nametr[0].find_all('td', class_='auto-style5')
			print (nametd[1].text).strip()			



			print td[2].text
			#print page.prettify()
		roll="1"
def mech_btech():
	roll="1"
	site="1"
	for i in range(4):
		roll = roll+str(i+2)
		site = site+str(i+2)
		for j in range(94):
			roll=roll+str(j+301)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/scheme"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="1"+str(i+2)
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='auto-style5')
			
			print td[2].text
			#print page.prettify()
		roll="1"
def ece_btech():
	roll="1"
	site="1"
	for i in range(4):
		roll = roll+str(i+2)
		site = site+str(i+2)
		for j in range(94):
			roll=roll+str(j+401)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/scheme"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="1"+str(i+2)
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='auto-style5')
			
			print td[2].text
			#print page.prettify()
		roll="1"
def barch():
	roll="1"
	site="1"
	for i in range(4):
		roll = roll+str(i+2)
		site = site+str(i+2)
		for j in range(94):
			roll=roll+str(j+601)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/scheme"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="1"+str(i+2)
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='auto-style5')
			
			print td[2].text
			#print page.prettify()
		roll="1"
def che_btech():
	roll="1"
	site="1"
	for i in range(3):
		roll = roll+str(i+3)
		site = site+str(i+3)
		for j in range(94):
			roll=roll+str(j+701)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/scheme"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="1"+str(i+3)
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='auto-style5')
			
			print td[2].text
			#print page.prettify()
		roll="1"
def cse_dual():
	roll="1"
	site="1"
	for i in range(2):
		roll = roll+str(i+4)+"MI"
		site = site+str(i+4)
		for j in range(94):
			roll=roll+str(j+501)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/scheme"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="1"+str(i+4)+"MI"
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='auto-style5')
			nametr=table[0].find_all('tr')
			nametd=nametr[0].find_all('td', class_='auto-style5')
			print (nametd[1].text).strip()			
			print td[2].text
			#print page.prettify()
		roll="1"
def ece_dual():
	roll="1"
	site="1"
	for i in range(1):
		roll = roll+str(i+5)+"MI"
		site = site+str(i+5)
		for j in range(94):
			roll=roll+str(j+401)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/scheme"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="1"+str(i+5)+"MI"
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='auto-style5')
			nametr=table[0].find_all('tr')
			nametd=nametr[0].find_all('td', class_='auto-style5')
			print (nametd[1].text).strip()			
			print td[2].text
			#print page.prettify()
		roll="1"
def cse_iiit():
	roll="IIITU1"
	site="iiituna1"
	for i in range(2):
		roll = roll+str(i+4)
		site = site+str(i+4)
		for j in range(94):
			roll=roll+str(j+101)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="IIITU1"+str(i+4)
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='ewTableAltRow')
			nametr=table[0].find_all('tr')
			nametd=nametr[0].find_all('td', class_='ewTableAltRow')
			print (nametd[0].text).strip()			
			print td[2].text
			#print page.prettify()
		roll="IIITU1"
def ece_iiit():
	roll="IIITU1"
	site="iiituna1"
	for i in range(2):
		roll = roll+str(i+4)
		site = site+str(i+4)
		for j in range(94):
			roll=roll+str(j+201)
			print roll
			form_data={
				'RollNumber' : roll
			}
			url = "http://14.139.56.15/"+str(site)+"/studentresult/details.asp"
#			print url
			response = requests.post(url, data = form_data)
			page=BeautifulSoup(response.text)
			table=page.find_all('table', class_='ewTable')
			roll="IIITU1"+str(i+4)
			if len(table)<6:
				continue
			tr=table[6].find_all('tr')
			td=tr[1].find_all('td', class_='ewTableAltRow')
			nametr=table[0].find_all('tr')
			nametd=nametr[0].find_all('td', class_='ewTableAltRow')
			print (nametd[0].text).strip()			
			print td[2].text
			#print page.prettify()
		roll="IIITU1"
cse_btech()
#ece_dual()
#ece_iiit()
#cse_iiit()
#cse_dual()
#eee_btech()
#mech_btech()

#civil_btech()
#barch()

 
import pdftotext
import re
from glob import glob
import openpyxl

rechnungen = glob ("Rechnungen/*.pdf")


from openpyxl import Workbook
wb = Workbook()

# grab the active worksheet
ws = wb.active
ws.append(['Datum', 'Uhrzeit', 'ArtNr', 'EAN', 'Bezeichnung', 'Pack', 'Einzelpreis', 'Inhalt', 'Preis', 'Menge', 'Gesamtpreis',  'MwSt', 'Stückpreis','Werbung'])

rex_artikel = re.compile('\s{2}(\d{6}\.\d)\s(\d{13})\s(.{0,45})([A-Z]{2})\s+(\d+,\d{3})\s+(\d+,?\d*)\s*(\d+,\d{2})\s*(\d+)\s+(\d+,\d{2})\s+([AB])\s+(\d+,\d{3})(\s{5}\w)?')

rex_datum = re.compile('((0[1-9]|[12]\d|3[01])\.(0\d|1[0-2])\.[12]\d{3})\s(([01]\d|2[0-4]|):[0-5]\d)')

def writeToExcel(data, datum, uhrzeit):
    # Rows can also be appended
    for a in data:
        print (a)
        b = list()
        b.append(datum);
        b.append(uhrzeit);
        b.append(a[0])
        b.append(a[1])
        b.append(a[2].strip())     # Bezeichnung
        b.append(a[3])             
        b.append(float(a[4].replace(',','.')))      # Einzelpreis
        b.append(float(a[5].replace(',','.')))      # Inhalt pro Pack
        b.append(float(a[6].replace(',','.')))      # Preis pro Pack
        b.append(int(a[7]))        # Anzahl
        b.append(float(a[8].replace(',','.')))      # Gesamtpreis
        b.append(a[9])
        b.append(float(a[10].replace(',','.')))      # Stückpreis
        if len(a) > 11:
            b.append(a[11])
        ws.append(b)




for rechnung in rechnungen:
    f = open (rechnung, 'rb')
    pdf = pdftotext.PDF(f)
    txt = ''
    for seite in pdf:
        txt = txt + seite;

    datum = rex_datum.findall(txt)
    print(datum[0][0])
    print(datum[0][3])
    
    m = rex_artikel.findall(txt)
    if len(m) > 0:
        writeToExcel(m, datum[0][0], datum[0][3])
    else:
        print(txt)
            
   
   
# Save the file
wb.save("alle.xlsx")

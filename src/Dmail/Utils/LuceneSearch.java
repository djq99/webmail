package Dmail.Utils;

import Dmail.dao.MailDao;
import Dmail.model.Email;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LuceneSearch {



    public static ArrayList<Email> Search(String indexPath,String folder, int userId, String searchField, String searchWords) throws SQLException {
        /*
         * create search index
         */
        ArrayList<Email> queryResult = new ArrayList<Email>();
        IndexWriter writer;
        MailDao mailDao = new MailDao();
        Connection conn = DbFactory.getConnection();
        ArrayList<Email> emailHeader = mailDao.returnMailHeader(userId, folder, conn);
        Document[] doc = new Document[emailHeader.size()];
        try {
            writer = new IndexWriter(indexPath, new StandardAnalyzer(), true);
            for (int i = 0; i < emailHeader.size(); i++) {
                doc[i] = new Document();
                doc[i].add(new Field("from", emailHeader.get(i).getFrom(), Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("id", emailHeader.get(i).getEmailID(), Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("isNew", emailHeader.get(i).getIsNew(), Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("attachment", emailHeader.get(i).isHasAttachment() + "", Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("subject", emailHeader.get(i).getTitle(), Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("size", emailHeader.get(i).getSize() + "", Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("date", emailHeader.get(i).getMailDate(), Field.Store.YES, Field.Index.TOKENIZED));
                writer.addDocument(doc[i]);
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * create search query
         */
        Query query;
        IndexSearcher searcher;
        try {
            searcher = new IndexSearcher(indexPath);
            String[] stringQuery = {searchWords};
            String[] fields = {searchField};
            Occur[] occ = {Occur.MUST};

            query = MultiFieldQueryParser.parse(stringQuery, fields, occ, new StandardAnalyzer());
            Hits hits = searcher.search(query);

           // System.out.println("hist.length "+hits.length());
            for (int i = 0; i < hits.length(); i++) {
                Email email = new Email();
                email.setFrom(hits.doc(i).get("from"));
                email.setTitle(hits.doc(i).get("subject"));
                email.setSize(Integer.valueOf(hits.doc(i).get("size")));
                email.setMailDate(hits.doc(i).get("date"));
                email.setEmailID(hits.doc(i).get("id"));
                email.setIsNew(hits.doc(i).get("isNew"));
                email.setHasAttachment(Boolean.parseBoolean(hits.doc(i).get("attachment")));
                queryResult.add(email);
            }
        } catch (Exception e) {
        }
        return queryResult;
    }

    public static int SearchResultNumber(String indexPath,String folder, int userId, String searchField, String searchWords) throws SQLException {
        /*
         * create search index
         */
        int queryResult = 0;
        indexPath = indexPath + "/" + userId;
        IndexWriter writer;
        MailDao mailDao = new MailDao();
        Connection conn = DbFactory.getConnection();
        ArrayList<Email> emailHeader = mailDao.returnMailHeader(userId, folder, conn);
        Document[] doc = new Document[emailHeader.size()];
        try {
            writer = new IndexWriter(indexPath, new StandardAnalyzer(), true);
            for (int i = 0; i < emailHeader.size(); i++) {
                doc[i] = new Document();
                doc[i].add(new Field("from", emailHeader.get(i).getFrom(), Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("id", emailHeader.get(i).getEmailID(), Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("isNew", emailHeader.get(i).getIsNew(), Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("attachment", emailHeader.get(i).isHasAttachment() + "", Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("subject", emailHeader.get(i).getTitle(), Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("size", emailHeader.get(i).getSize() + "", Field.Store.YES, Field.Index.TOKENIZED));
                doc[i].add(new Field("date", emailHeader.get(i).getMailDate(), Field.Store.YES, Field.Index.TOKENIZED));
                writer.addDocument(doc[i]);
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * create search query
         */
        Query query;
        IndexSearcher searcher;
        try {
            searcher = new IndexSearcher(indexPath);
            String[] stringQuery = {searchWords};
            String[] fields = {searchField};
            Occur[] occ = {Occur.SHOULD};

            query = MultiFieldQueryParser.parse(stringQuery, fields, occ, new StandardAnalyzer());
            Hits hits = searcher.search(query);
            queryResult = hits.length();


        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryResult;
    }
}

